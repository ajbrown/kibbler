package kibbler

class AdoptionContractTemplate {
    Organization organization
    Set<String> terms

    Date dateCreated
    User createdBy
    Date lastUpdated
    User updatedBy

    static belongsTo = [organization: Organization]

    static constraints = {
        createdBy nullable: true
        updatedBy nullable: true

    }
}
