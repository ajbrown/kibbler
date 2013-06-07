package kibbler

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.cloudinary.Cloudinary
import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.imgscalr.Scalr
import org.springframework.web.multipart.commons.CommonsMultipartFile

import javax.imageio.ImageIO

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class PetsController {

    static final int PHOTO_MAX_WIDTH = 600
    static final PHOTO_ALLOWED_CONTENT_TYPES = [ 'image/jpeg', 'image/x-png', 'image/png' ]

    def organizationService
    def petService
    def personService
    def springSecurityService
    def eventService

    def grailsApplication
    AmazonS3Client amazonS3Client
    ObjectMapper objectMapper
    Cloudinary cloudinary

    def beforeInterceptor = {
        def skipActions = ['index','create']

        if( params.action in skipActions ) {
            return true
        }

        def pet = petService.read( params.id )

        if( !pet ) {
            response.sendError( 404, 'The specified pet was not found.' )
            return false
        }

        if( !springSecurityService.currentUser.belongsTo( pet.organization ) ) {
            response.sendError( 403, "You do not have access to this pet." )
        }

        params.pet = pet
    }

    def index() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def user = springSecurityService.currentUser as User

        //Get all pets for all organizations this user belongs to.
        //TODO we may want to only return for the currently active organization
        // but for now we're developing in single organization mode anyway.
        def pets = user.organizations.collectMany{ petService.readAllForOrg( it ) }

        withFormat{
            json{
                resp.data = pets
                render resp as JSON
            }
        }
    }

    def read() {
        lastModified params.pet.lastUpdated

        def resp = new JSONResponseEnvelope( status: 200 )

        withFormat{
            json{
                resp.data = params.pet
                render resp as JSON
            }
        }
    }

    def update() {
        def user = springSecurityService.currentUser as User
        def resp = new JSONResponseEnvelope( status: 201 )

        def fields = objectMapper.readValue( request.inputStream, Map.class )
        def saved = petService.updateFields( fields, params.pet, user )

        //TODO if saving failed due to optimistic locking, refresh and try again.

        if( !saved ) {
            resp.status = 400
            resp.errors = pet.errors.allErrors
            resp.data = pet
        } else {
            resp.data = saved
        }

        withFormat{
            json{ render resp as JSON }
        }
    }

    def create( CreatePetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        def org  = organizationService.read( cmd.orgId )

        //make sure the organization belongs to the user
        if( !user.belongsTo( org ) ) {
            throw new Exception( "You don't belong to the specified organization." )
        }

        if( cmd.validate() ) {
            def pet = new Pet(
                    givenName: cmd.givenName,
                    sex: cmd.sex,
                    type: cmd.species,
                    breed: cmd.breed
            )

            def saved = petService.create( org, pet, user )
            if ( saved ) {
                jsonResponse.status = 200
                jsonResponse.data = saved
            } else {
                jsonResponse.status = 400
                jsonResponse.errors.addAll pet.errors.allErrors
            }

        } else {
            jsonResponse.status = 400
            jsonResponse.errors.addAll cmd.errors.allErrors
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def adopt( AdoptPetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 201 )

        if( cmd.validate() ) {
            def adopter = personService.read( cmd.adopter )

            //Make sure the adopter exists.
            if( !adopter ) {
                //TODO better exceptions
                throw new Exception( 'Adopter does not exist' )
            }

            if( !petService.adopt( params.pet, adopter, user ) ) {
                response.status = 500
                jsonResponse.errors = params.pet.errors.allErrors
            }

            jsonResponse.data = params.pet
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def foster( FosterPetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        if( cmd.validate() ) {
            def foster = personService.read( cmd.fosterId )

            //Make sure the foster exists.
            if( !foster ) {
                //TODO better exceptions
                throw new Exception( 'Foster does not exist' )
            }

            if( !petService.foster( params.pet, foster, user ) ) {
                jsonResponse.status = 500
                jsonResponse.errors = params.pet.errors.allErrors
            }

            jsonResponse.data = params.pet
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def hold( HoldPetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        if( cmd.validate() ) {

            if( !petService.hold( params.pet, user ) ) {
                jsonResponse.status = 500
                jsonResponse.errors = params.pet.errors?.allErrors
            }

            jsonResponse.data = params.pet
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def reclaim() {
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        def resp = petService.reclaim( params.pet, user )

        jsonResponse.data = resp

        withFormat {
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def history() {
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        def events = eventService.listByPet( params.pet, params.days ?: 30 )

        if( events ) {
            lastModified events.first().dateCreated
        }

        jsonResponse.data = events.collect{
            [ event: it, message: eventService.translateMessage( it, request.locale ) ]
        }

        withFormat {
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def photos() {
        def par = params
        def jsonResponse = new JSONResponseEnvelope()
        def req = request

        def files = request.multipartFiles["photos"]
        def bucket = grailsApplication.config.petphotos.uploadBucket.toString()

        //make sure we have a valid orgianzation, since it's used to prefix the photos.

        // Each file is first uploaded to amazon s3 to retain the original.  We then shrink photos to a maximum of
        // 600 pixes wide, and upload those to cloudinary where transformations can be done.
        def photos = files.collect{ CommonsMultipartFile file ->

            //TODO we should group all of the local processing, then do the uploads

            def photo = new Photo()

            if( !PHOTO_ALLOWED_CONTENT_TYPES.contains( file.contentType.toLowerCase() ) ) {
                log.info "Skipping a file, wrong content type: ${file.contentType}"
                //TODO notify the user.
                return
            }

            def id = UUID.randomUUID().toString().replace( '-', '' )
            photo.s3key = "${params.pet.organization.id.toString()}/${params.pet.id.toString()}/${id}.jpg"
            photo.standardUrl = "http://${bucket}.s3.amazonaws.com/${photo.s3key}"

            def img = ImageIO.read( file.inputStream )
            if( img.width > PHOTO_MAX_WIDTH ) {
                img  = Scalr.resize( img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, PHOTO_MAX_WIDTH )
            }

            def outStream = new ByteArrayOutputStream()
            ImageIO.write( img, "jpg", outStream )
            def inStream = new ByteArrayInputStream( outStream.toByteArray() )

            def meta = new ObjectMetadata()
            meta.cacheControl = 'max-age=31536000'
            meta.contentType  = 'image/jpg'
            meta.setHeader( 'X-Kibbler-Org-Id', params.pet.id.toString() )
            meta.setHeader( 'X-Kibbler-Pet-Id', params.pet.organization.id.toString() )

            //Upload the photo to the S3 bucket
            def s3resp = amazonS3Client.putObject( bucket, photo.s3key, inStream, meta )

            //Upload the photo to cloudinary
            def map = Cloudinary.asMap( "tags", "pet,org_${params.pet.organization.id},pet_${params.pet.id}" )
            photo.cloudinaryData = cloudinary.uploader().upload( photo.standardUrl, map )
            photo
        }

        def resp = petService.addPhotos( params.pet, photos, springSecurityService.currentUser )

        withFormat{
            javascript {

                params.callback = params.callback ?: 'console.log'

                jsonResponse.status = response.status
                jsonResponse.data = photos
                def text = jsonResponse as JSON

                render "<script>window.parent.${params.callback}(${text});</script>"
            }
        }
    }
}