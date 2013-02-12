package bigbird

import org.bson.types.ObjectId

class Person {

    ObjectId id

    String firstName
    String lastName

    Date dateCreated
    User createdBy
    Date lastUpdated
    User updatedBy

    static belongsTo = [
            organization: Organization
    ]


    static constraints = {
    }
}
