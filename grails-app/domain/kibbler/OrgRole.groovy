package kibbler



class OrgRole {
    User user
    String role
    Organization organization

    User createdBy
    Date dateCreated

    static belongsTo = Organization

    static mapping = {
        version false
    }

    static constraints = {
        createdBy nullable: true
    }
}
