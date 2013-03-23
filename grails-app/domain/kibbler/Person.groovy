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

    Boolean adopter = false
    Boolean foster = false
    Boolean available = true
    Boolean doNotAdopt = false
    User linkedAccount


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
            ( !value || obj.organization in obj.linkedAccount.organizations ) ?: ['organization.mismatch']
        }

        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

}
