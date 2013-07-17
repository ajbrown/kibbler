package kibbler

import org.bson.types.ObjectId

class Pet {

    def slugGeneratorService

    static final STATUS_OPTIONS = [ 'available','hold','fostered','adopted','deceased' ]

    ObjectId id

    String assignedId
    String slug

    String givenName
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
    static hasMany = [ photos: Photo, adoptions: AdoptionRecord, fosterings: FosterRecord ]

    static mapping = {
        organization index: true
        slug index: true
        sort "givenName"
    }

    static constraints = {
        id()
        organization()
        type()

        breed nullable: true
        sex   nullable: true, inList: [ 'male','female' ]

        status inList: STATUS_OPTIONS
        givenName blank: false

        age nullable: true
        markings nullable: true
        weight nullable: true
        color nullable: true

        adopter nullable: true, validator:  { Person val, Pet obj ->
            if( !val ) { return true }
            val.organizationId == obj.organizationId ?: ['organization.mismatch']
        }
        foster  nullable: true, validator: { Person val, Pet obj ->
            if( !val ) { return true }
            val.organizationId == obj.organizationId ?: ['organization.mismatch']
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

    def getCurrentContract() {
        if( !status == 'adopted' || !adopter ) {
            return null
        }

        AdoptionContract.createCriteria().list{
            eq "adopter", adopter
            order "dateCreated", "desc"
        }?.first()
    }

    def beforeInsert() {
        generateSlug()
    }

    def generateSlug() {
        slug = slugGeneratorService.generateSlug( this.class, "slug", "${breed ?: ''} ${givenName ?: ''}".trim(), true )
    }
}
