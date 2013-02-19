package kibbler

import grails.converters.JSON
import org.bson.types.ObjectId

class Pet {

    ObjectId id

    String assignedId

    String givenName
    String description
    Species type
    String breed
    String sex
    Integer weight

    PetVitals vitals = new PetVitals()

    String status = 'available'
    String notes

    Date dateCreated
    Date lastUpdated
    User createdBy
    User lastUpdatedBy

    static belongsTo = [ organization: Organization ]
    static hasMany = [ photos: Photo ]
    static embedded = [ 'vitals' ]

    static mapping = {
        sort "givenName"
    }

    static constraints = {

        assignedId nullable: true
        givenName blank: false

        breed nullable: true
        sex   nullable: true, inList: [ 'male','female' ]
        weight nullable: true

        status inList: [ 'available','hold','fostered','adopted','deceased' ]

        createdBy nullable: true
        lastUpdatedBy nullable: true

        description nullable: true
        notes nullable: true
    }
}
