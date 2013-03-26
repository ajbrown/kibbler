package kibbler

import org.bson.types.ObjectId

class OrgRole {

    ObjectId id
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
