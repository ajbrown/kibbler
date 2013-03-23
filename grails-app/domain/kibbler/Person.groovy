package kibbler

import org.bson.types.ObjectId

class Person {

    ObjectId id

    String name
    String company
    String address
    String notes

    String phone
    String email

    Boolean adopter
    Boolean foster
    Boolean available = true
    User linkedAccount

    Boolean doNotAdopt = false

    Date dateCreated
    User createdBy
    Date lastUpdated
    User lastUpdatedBy

    static hasMany = [ fostering: Pet, adopted: Pet ]
    static mappedBy = [ fostering: "foster", adopted: "adopter" ]

    static belongsTo = [
            organization: Organization
    ]

    static mapping = {
        sort "name"
    }

    static constraints = {
        company nullable: true
        address nullable: true
        notes nullable: true
        phone nullable: true
        email nullable: true
        adopter nullable: true
        foster nullable: true
        linkedAccount nullable: true, validator: { value, Person obj ->

        }

        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

}
