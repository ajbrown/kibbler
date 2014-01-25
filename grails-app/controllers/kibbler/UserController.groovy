package kibbler

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import kibbler.request.ResetPassword

import static grails.async.Promises.*

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


    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def reset( ResetPassword cmd ) {
        def user
        def correctCode = false

        //TODO we need to prevent the number of times this can be called.  Essentially, we've given an easy way for
        //a hacker to brute force simpler passwords.  Since the activation code is significantly shorter than the password
        //and doesn't actually expire.  If we expire tokens, or make sure the same account can't spam reset attempts with a different
        //code, we'd be good.

        //if the user has entered an activation code, validate it
        if( cmd.code && cmd.email ) {
            user = User.findByEmailAndActivationCodeIlike( cmd.email, cmd.code )
            if( !user ) {
                log.warn "No use found with email ${cmd.email} and activation code ${cmd.code}"
                redirect controller: 'user', action: 'reset'
                return
            }

            correctCode = true

            //If they've entered a password, finalize the reset process
            if( request.post && cmd.password ) {
                def done = userService.finalizeResetPassword( user, cmd.password )
                if( done ) {
                    flash.message = "Your password has been successfully reset."
                    springSecurityService.reauthenticate( user.email, cmd.password )
                    redirect controller: 'dashboard', action: 'index'
                    return
                } else {
                    log.warn "There was an issue resetting the password for user ${user.email}"
                    flash.message = "There was an issue resetting your password.  Please try again, and contact us at support@kibbler.org if you continue having problems"
                    redirect action: "reset"
                    return
                }
            }

        //The request is a post, and the email address was supplied
        } else if( request.post && cmd.email ) {
            user = userService.findByEmail( cmd.email )
            if( user ) {
                userService.initiatResetPassword( user )
            }
        }

        def model = [ cmd: cmd, correctCode: correctCode ]


        withFormat{
            html{ return model }
            json { render model as JSON }
        }
    }

}
