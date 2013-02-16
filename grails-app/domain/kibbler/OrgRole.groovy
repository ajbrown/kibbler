package kibbler

import org.bson.types.ObjectId

class OrgRole {

    ObjectId id
    User user
    Organization organization
    String role

    static constraints = {
    }
}
