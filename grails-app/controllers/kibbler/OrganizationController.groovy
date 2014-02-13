package kibbler

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class OrganizationController extends RestfulController<Organization> {

    def springSecurityService

    OrganizationController() {
        super( Organization )
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected List<Organization> listAllResources( Map params ) {
        params.max = params.max ?: 100

        def limitOrgs = []

        //if we're accessing this list through the /users/{id}/organizations mapping, we only want to return organizations
        //that the user is a member of.
        if( params.userId ) {

            //allow userId to be "me", indicating the current user.
            def user = params.userId == 'me' ? (springSecurityService.currentUser as User) : User.read( params.userId )
            limitOrgs = user.organizations*.id

            if( !limitOrgs ) {
                return []
            }
        }

        Organization.createCriteria().list( max: params.max ) {

            if( limitOrgs ) {
                'in' ( "id", limitOrgs )
            }

            parametersToBind.findAll{ it.value }.each{
                eq it.key, it.value
            }

            order "name"
        }
    }

}
