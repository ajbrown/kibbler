package kibbler

import grails.converters.JSON
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

    //Vitals
    Boolean heartworm
    Boolean housebroken
    Boolean microchipped
    String  microchipId
    Boolean neutered
    Boolean specialNeeds

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
