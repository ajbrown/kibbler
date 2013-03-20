package kibbler

import org.bson.types.ObjectId

class FosterRecord {
    ObjectId id

    Organization organization
    Person foster
    Pet pet

    User createdBy
    Date dateCreated
    User updatedBy
    Date lastUpdated

    static belongsTo = Pet

    static mapping = {
        version false
    }

    static constraints = {
        createdBy nullable: true
        updatedBy nullable: true
    }
}
