package kibbler

class FosterRecord {
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
        lastUpdated nullable: true
    }
}
