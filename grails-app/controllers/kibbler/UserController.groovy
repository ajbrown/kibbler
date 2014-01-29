package kibbler

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import kibbler.request.ResetPasswordCommand

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class UserController {

    def springSecurityService
    def organizationService
    def userService

    def index() {
        def user = springSecurityService.getCurrentUser() as User
        def organizations = user.organizations

        request.withFormat{
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
    def reset( ResetPasswordCommand cmd ) {
        def model = [:]
        model << cmd.properties

        //TODO we need to prevent the number of times this can be called.  Essentially, we've given an easy way for
        //a hacker to brute force simpler passwords.  Since the activation code is significantly shorter than the password
        //and doesn't actually expire.  If we expire tokens, or make sure the same account can't spam reset attempts with a different
        //code, we'd be good.

        if( request.post ) {

            //Attempting to update the password.  We much have a valid email and confirmation code.
            if( cmd.password && cmd.code && cmd.email ) {

                model.user = User.findByEmailAndActivationCode( cmd.email.toLowerCase(), cmd.code.toLowerCase() )
                model.success = model.user && userService.finalizeResetPassword( model.user, cmd.password )
                model.action = 'update-password'

                if( model.success ) {
                    //Mark the user as logged in so they're not prompted for their password when we redirect.
                    springSecurityService.reauthenticate( model.user.email, cmd.password )
                }

            } else if( cmd.code && cmd.email ) {

                //Validating a confirmation code

                model.user = User.findByEmailAndActivationCodeIlike( cmd.email, cmd.code )
                model.success = model.user ? true : false
                model.action = 'check-confirmation-code'

            } else if( cmd.email ) {

                //Requesting a new confirmation code be sent.
                model.user = userService.findByEmail( cmd.email )
                model.success = model.user && userService.initiateResetPassword( model.user )
                model.action = 'send-confirmation-code'

            }
        }

        request.withFormat {
            html {
                return model
            }

            json {
                //don't spit out the password
                model.remove( 'password' )
                render model as JSON
            }
        }

    }

}
