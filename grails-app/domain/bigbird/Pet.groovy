package bigbird

import org.bson.types.ObjectId

class Pet {

    ObjectId id

    Integer assignedId

    String givenName
    String description
    PetType type
    String breed
    String sex

    String status = 'available'
    String notes

    Date dateCreated
    Date lastUpdated
    User createdBy
    User lastUpdatedBy

    static belongsTo = [ organization: Organization ]
    static hasMany = [ photos: Photo ]

    static constraints = {

        givenName blank: false

        breed nullable: true
        sex   nullable: true, inList: [ 'male','female' ]

        status inList: [ 'available','hold','fostered','adopted','deceased' ]

        createdBy nullable: true
        lastUpdatedBy nullable: true
    }
}
