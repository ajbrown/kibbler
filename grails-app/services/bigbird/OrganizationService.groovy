package bigbird

class OrganizationService {

    def createOrganization( String name, User creator ) {
        def org = new Organization( name: name )
        org.addToMembers( [ role: 'admin', user: creator ] )
        org.save()
    }

    /**
     * Add a user to an existing organization.
     * @param org
     * @param addded
     * @param role
     * @param addedBy
     * @return
     */
    def addUserToOrganization( Organization org, User added, String role = 'user', User addedBy = null ) {
        def exists = org.members.find{ it.user == added }

        if( !exists ) {
            org.addToMembers( [ role: role, user: added ] )
            org.save()
        } else if( exists.role != role ) {
            exists.role = role
            exists.save()
        }
    }

    def listUserOrganizations( User user ) {
        user.organizations
    }

}
