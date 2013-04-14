package kibbler

import org.bson.types.ObjectId

class Pet {

    static final STATUS_OPTIONS = [ 'available','hold','fostered','adopted','deceased' ]

    ObjectId id

    String assignedId

    String givenName
    String description
    Species type
    String breed
    String sex
    Integer weight

    //TODO these could probably be consildated. Think about it once we get further along in the prototype.
    Person adopter
    Person foster

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
        sort "givenName"
    }

    static constraints = {

        assignedId nullable: true
        givenName blank: false

        breed nullable: true
        sex   nullable: true, inList: [ 'male','female' ]
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

        status inList: STATUS_OPTIONS

        createdBy nullable: true
        lastUpdatedBy nullable: true

        description nullable: true
        notes nullable: true
    }
}
