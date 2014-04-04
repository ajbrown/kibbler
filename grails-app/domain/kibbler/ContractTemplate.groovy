package kibbler

class ContractTemplate {
    Organization organization
    List<String> terms

    Date dateCreated
    User createdBy
    Date lastUpdated
    User updatedBy

    static belongsTo = [ organization: Organization ]
    static hasMany = [ terms: String ]

    static constraints = {
        createdBy nullable: true
        updatedBy nullable: true
    }

    static mapping = {
        organization index: true
        terms joinTable: [ column: 'term', sqlType: 'text'], indexColumn: [ name: 'list_order' ]
    }
}
