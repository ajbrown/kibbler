package kibbler

import grails.util.Environment
import org.bson.types.ObjectId

class Pet {

    static THUMBNAIL_PREFIX = 'http://res.cloudinary.com/hikkwdvwy/image/upload/w_150,h_150,c_thumb,g_faces,r_6'

    def slugGeneratorService

    static final STATUS_OPTIONS = [ 'available','hold','fostered','adopted','deceased' ]

    ObjectId id

    String assignedId
    String slug

    String name
    String description
    Species type
    String breed
    String sex
    Integer age
    Integer weight
    String markings

    //TODO these could probably be consildated. Think about it once we get further along in the prototype.
    Person adopter
    Person foster

    Set photos
    Set documents

    //Vitals
    Boolean heartworm
    Boolean housebroken
    Boolean microchipped
    String  microchipId
    String color

    Boolean neutered
    Boolean specialNeeds

    String status = 'available'
    String notes

    Date dateCreated
    Date lastUpdated
    User createdBy
    User lastUpdatedBy

    static belongsTo = [ organization: Organization ]
    static hasMany = [
            documents: Document,
            photos: Photo,
            adoptions: AdoptionRecord,
            fosterings: FosterRecord
    ]

    static mapping = {
        organization index: true
        slug index: true
        sort "name"
    }

    static constraints = {
        id()
        organization()
        type()

        breed nullable: true
        sex   nullable: true, inList: [ 'male','female' ]

        status inList: STATUS_OPTIONS
        name blank: false

        age nullable: true
        markings nullable: true
        weight nullable: true
        color nullable: true

        adopter nullable: true, validator:  { Person val, Pet obj ->
            if( !val ) { return true }
            val.organization == obj.organization ?: ['organization.mismatch']
        }
        foster  nullable: true, validator: { Person val, Pet obj ->
            if( !val ) { return true }
            val.organization == obj.organization ?: ['organization.mismatch']
        }

        heartworm nullable: true
        housebroken nullable: true
        microchipped nullable: true
        microchipId nullable: true
        neutered nullable: true
        specialNeeds nullable: true

        createdBy nullable: true
        lastUpdatedBy nullable: true

        description nullable: true
        notes nullable: true
        photos nullable: true

        assignedId nullable: true
    }

    /**
     * Returns the current contract for the pet's adoption.  If the Pet is not currently adopted,
     * or there was no contract for the latest adoption,  no contract will be returned.
     *
     * @return
     */
    def AdoptionContract getCurrentContract() {
        if( !status == 'adopted' || !adopter ) {
            return null
        }

        def adoptionRecord = AdoptionRecord.createCriteria().get {
            eq "adopter", adopter
            eq "pet", this
            order "dateCreated", "desc"
        }

        adoptionRecord?.contract
    }

    def beforeValidate() {
        //Nasty hack to allow tests to run
        if( Environment.current == Environment.TEST ) {
            slug = name.toLowerCase().replace( " ", "-" )
        }

        if( !slug ) {
            generateSlug()
        }
    }

    def getThumbnail() {
        def url

        if( !photos ) {
            return
        }

        def first = photos.first()

        if( first && first.cloudinaryData ) {
            url = THUMBNAIL_PREFIX + first.cloudinaryData.public_id + '.' + first.cloudinaryData.format
        }

        url
    }

    def generateSlug() {
        slug = slugGeneratorService.generateSlug( this.class, "slug", "${breed ?: ''} ${name ?: ''}".trim(), true )
    }
}
