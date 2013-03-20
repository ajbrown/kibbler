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

    Boolean doNotAdopt = false

    Date dateCreated
    User createdBy
    Date lastUpdated
    User lastUpdatedBy

    static hasMany = [ fostering: Pet, adopting: Pet ]
    static mappedBy = [ fostering: "foster", adopting: "adopter" ]

    static belongsTo = [
            organization: Organization
    ]


    static constraints = {
        company nullable: true
        address nullable: true
        notes nullable: true
        phone nullable: true
        email nullable: true
        adopter nullable: true
        foster nullable: true

        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

}
