package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class UserController {

    def springSecurityService
    def organizationService
    def userService

    def index() {
        def user = springSecurityService.getCurrentUser() as User
        def organizations = user.organizations

        withFormat{
            json {
                def data = [:]
                data.id = user.id.toString()
                data.email = user.email
                data.organizations = organizations
                render data as JSON
            }
        }
    }

    /**
     * Switch to an organization
     */
    def switchTo() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def user = springSecurityService.currentUser as User

        if( params.org ) {

            def org = organizationService.read( params.org )
            //make sure the current user is a member of the organization
            if( org && user.belongsTo( org ) ) {
                session.activeOrgId = org.id
                resp.data = org
            } else {
                resp.status = 404
                resp.errors = [ 'Organization not found, or user doesn\'t belong to this organization' ]
            }
        } else {
            resp.status = 400
            resp.errors = [ 'You must specify an organization ID' ]
        }

        response.status = resp.status
        render resp as JSON
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def activate( ActivateUserCommand cmd ) {
        cmd.clearErrors()

        def user = userService.findByActivationCode( cmd.code )

        //Make sure there's a user with the specified activation code.

        if( request.post && cmd.validate() ) {
            if( user ) {
                user.name = cmd.name
                user.password = cmd.password
                user.activationCode = null
                if( userService.activate( user ) ) {
                    springSecurityService.reauthenticate( user.email )
                    redirect( controller: 'dashboard', action: 'index' )
                    return
                }
            }

            cmd.errors.rejectValue( "cmd", "activationCode.doesNotExist", "Invalid activation code")
        }

        [ cmd: cmd, user: user ]
    }

}
