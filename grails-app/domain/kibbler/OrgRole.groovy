package kibbler



class OrgRole {
    User user
    String role
    Organization organization

    User createdBy
    Date dateCreated
    Date lastUpdated

    static belongsTo = [organization: Organization, user: User]

    static mapping = {
        organization index: "idx_orgrole_org_user"
        user index: "idx_orgrole_org_user"
    }

    static constraints = {
        createdBy nullable: true
    }
}
