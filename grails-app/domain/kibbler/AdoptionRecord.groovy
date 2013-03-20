package kibbler

import org.bson.types.ObjectId

/**
 * Domain class represting an adoption record.
 */
class AdoptionRecord {
    ObjectId id

    Organization organization
    Person adopter
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
