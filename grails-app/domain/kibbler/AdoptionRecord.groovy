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
    AdoptionContract contract

    User createdBy
    Date dateCreated
    User updatedBy
    Date lastUpdated

    static belongsTo = Pet

    static mapping = {
        version false
        stateless true
    }

    static constraints = {
        contract nullable: true
        createdBy nullable: true
        updatedBy nullable: true
    }
}
