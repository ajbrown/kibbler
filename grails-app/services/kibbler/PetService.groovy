package kibbler

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.StorageClass
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CachePut
import grails.plugin.cache.Cacheable
import org.bson.types.ObjectId
import org.springframework.cache.CacheManager

class PetService {

    private static final SIGNATURE_XML_HEADER = '<?xml version="1.0" encoding="UTF-8" standalone="no"?><!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">'

    def eventService
    def pdfRenderingService
    def grailsApplication


    AmazonS3Client amazonS3Client

    /**
     * Remove pets from the cache.  Allows referencing the object either by String id, ObjectId, or by Pet instance.
     * @param obj
     */
    static determineCacheKey( obj ) {
        def key
        if( obj instanceof ObjectId ) {
            key = obj.toString()
        } else if( obj instanceof String ) {
            key = obj
        } else if( obj instanceof Pet ) {
            key = obj.id.toString()
        }
        key
    }

    def Pet read( String id ) {
        Pet.read( new ObjectId( id ) )
    }

    def readAllForOrg( Organization org ) {
        Pet.findAllByOrganization( org )
    }

    def Pet create( Organization org, Pet pet, User creator = null) {
        pet.organization = org
        pet.createdBy = creator

        def saved = pet.insert( failOnError: true )
        if( saved ) {
            eventService.create( EventType.PET_ADD, pet, creator )
        }
        saved
    }

    def findBySlug( String slug ) {
        Pet.findBySlug( slug  )
    }

    def updateFields( Map fields, Pet pet, User user ) {
        fields.each { key, value ->
            pet[ key ] = value
        }
        pet.lastUpdatedBy = user
        def saved = pet.save()

        if( saved ) {
            eventService.create( EventType.PET_UPDATE, pet, user, [fields] )
        }
        saved
    }

    /**
     * Adopt a pet to the specified adopter.
     * TODO only allow a pet to be adopted if they're in a valid state.
     * @param org
     * @param pet
     * @param adopter
     * @param creator
     */
    def adopt( Pet pet, Person adopter, AdoptionContract contract = null, User creator = null ) {

        def record = new AdoptionRecord(
                organization: pet.organization,
                pet: pet,
                adopter: adopter,
                contract: contract,
                createdBy: creator
        )

        if( !record.insert( failOnError: true ) ) {
            //TODO better exception handling
            throw new Exception( 'Could not create adoption record, aborting adoption' )
        }

        pet.adopter = adopter
        pet.foster = null
        pet.status = 'adopted'
        pet.lastUpdatedBy = creator

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_ADOPT, pet, creator, [adopter, record] )
        }
        saved
    }

    def createContract( Pet pet, Person adopter, Map signatures, User creator = null ) {
        def saved
        def contract = new AdoptionContract(
                id: new ObjectId(),
                adopterSignature: new String( signatures.svg )
        )

        contract.adopterSignatureUrl = uploadSignature( contract )

        //generate the contract's pdf
        def pdfModel = [
                pet: pet,
                organization: pet.organization,
                adopter: adopter,
                signaturePng: signatures.png
        ]

        def bucket = grailsApplication.config.contractsBucket

        //Render the PDF into a stream that we can send to Amazon S3
        def outputStream = new ByteArrayOutputStream()
        pdfRenderingService.render( [ model: pdfModel, template: '/pdf/adoption' ], outputStream )
        def inputStream = new ByteArrayInputStream( outputStream.toByteArray() )

        contract.pdfS3key = "contracts/${pet.organization.slug.encodeAsURL()}/${contract.id}.pdf"

        def meta = new ObjectMetadata()
        meta.cacheControl = 'max-age=31536000'
        meta.contentType = 'application/pdf'
        meta.setHeader( 'X-Kibbler-Org-Id', pet.organization.id.toString() )
        meta.setHeader( 'X-Kibbler-Pet-Id', pet.id.toString() )

        def s3resp = amazonS3Client.putObject( bucket, contract.pdfS3key, inputStream, meta )

        if( !s3resp ) {
            throw new Exception( 'Could not upload contract!' )
        }

        contract.insert( failOnError:  true )

        adopt( pet, adopter, creator, contract )
    }

    /**
     * Foster a pet to the specified foster.
     * @param org
     * @param pet
     * @param foster
     * @param creator
     * @return
     */
    def foster( Pet pet, Person foster, User creator = null ) {
        def record = new FosterRecord( pet: pet, foster: foster, createdBy: creator, organization: pet.organization )
        if( !record.insert( failOnError: true ) ) {
            //TODO better exception handling
            throw new Exception( 'Could not create fostering record, aborting fostering' )
        }

        pet.adopter = null
        pet.foster = foster
        pet.status = 'fostered'
        pet.lastUpdatedBy = creator

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_FOSTER, pet, creator, [foster, record] )
        }
        saved
    }

    /**
     * Reclaim a pet, removing the pet from their current foster or adopter.
     * @param pet
     * @param updater
     * @return
     */
    def reclaim( Pet pet, User updater = null ) {

        pet.adopter = null
        pet.foster  = null
        pet.status  = 'available'
        pet.lastUpdatedBy = updater

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_RECLAIM, pet, updater )
        }
        saved
    }

    /**
     * Place the pet on hold, so it cannot be adopted or fostered.
     * @param pet
     * @param creator
     * @return
     */
    def hold( Pet pet, User creator = null ) {
        pet.adopter = null
        pet.foster  = null
        pet.status  = 'hold'
        pet.lastUpdatedBy = creator

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_HOLD, pet, creator )
        }
        saved
    }

    def addPhotos( Pet pet, List<Photo> photos, User creator = null ) {
        photos.each{ pet.addToPhotos( it ) }
        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_ADD_PHOTO, pet, creator, [photos] )
        }
        saved?.photos
    }

    def addFiles( Pet pet, List<Document> files, User creator = null ) {
        files.each{ pet.addToDocuments( it ) }
        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_ADD_DOC, pet, creator, [files] )
        }
        saved?.documents
    }

    /**
     * Submits an adoption application
     * @param pet
     * @param cmd
     * @return
     */
    def AdoptionApplication submitApplication( Pet pet, AdoptionApplyCommand cmd ) {
        def saved
        if( cmd.validate() ) {
            def app   = new AdoptionApplication( pet: pet, name: cmd.name, email: cmd.email, phone: cmd.phone )
            saved = app.save()

            if( saved ) {
                eventService.create( EventType.PET_APPLICATION, pet, null, [app] )
            }
        }

        saved
    }

    private String uploadSignature( AdoptionContract contract ) {
        def bucket = grailsApplication.config.contractsBucket
        def meta = new ObjectMetadata()
        meta.cacheControl = 'max-age=31536000'
        meta.contentType  = 'image/svg+xml'
        meta.expirationTime = new Date() + 365
        meta.contentLength = contract.adopterSignature.bytes.length

        def key = "contracts/${contract.pet.organization.slug}/${contract.id}-signature.svg"
        def inputStream = new ByteArrayInputStream( contract.adopterSignature.getBytes() )
        def request = new PutObjectRequest( bucket, key, inputStream, meta )
                .withCannedAcl( CannedAccessControlList.PublicRead )
                .withStorageClass( StorageClass.Standard )

        amazonS3Client.putObject( request )
        "http://${bucket}.s3.amazonaws.com/${key}"
    }

}
