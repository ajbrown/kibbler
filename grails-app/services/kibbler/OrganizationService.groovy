package kibbler

import org.bson.types.ObjectId

class OrganizationService {

    /**
     * Load an Organization.
     * @param id
     * @return
     */
    def Organization read( String id ) {
        Organization.findById( new ObjectId( id ) )
    }

    /**
     * Create a new organization.
     * @param name The name of the organization.
     * @param creator the user that is creating the organization.  They will be set as the administrator.
     * @return
     */
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
    def addUserToOrganization( Organization org, User added, User addedBy = null, String role = 'user' ) {
        def exists = org.members.find{ it.user == added }

        if( !exists ) {
            org.addToMembers( new OrgRole( role: role, user: added, createdBy: addedBy ) )
            org.save( failOnError: true )
        } else if( exists.role != role ) {
            exists.role = role
            exists.save( failOnError: true )
        }
    }

    /**
     * List all of the organizations a user is a member of.
     * @param user
     * @return
     */
    def listUserOrganizations( User user ) {
        user.organizations
    }

}
