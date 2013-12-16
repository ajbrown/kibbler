package kibbler

/**
 * Domain class represting an adoption record.
 */
class AdoptionRecord {
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
    }

    static constraints = {
        contract nullable: true
        createdBy nullable: true
        updatedBy nullable: true
    }
}
