package org.kibbler

class OrgRole {

    enum Role { ADMIN, MEMBER }

    Organization organization
    User user
    Role role = Role.MEMBER

    Date dateCreated
    Date lastUpdated

    static belongsTo = [organization: Organization, user: User]

    static constraints = {
    }
}
