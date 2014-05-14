package kibbler

import grails.plugin.springsecurity.annotation.Secured
import kibbler.request.ResetPasswordCommand
import org.springframework.http.HttpStatus

@Secured(['isAuthenticated()'])
class UserController extends RestfulController<User> {

    UserController() {
        super(User)
    }

    def springSecurityService
    def organizationService
    def userService
    def tokenGenerator
    def authenticationTokenStorageService

    @Secured(['permitAll'])
    def activate( ActivateUserCommand cmd, Boolean auth ) {
        if( !cmd.validate() ) {
            respond cmd.errors
            return
        }

        def token = VerificationToken.findByToken( cmd.code )
        def user = userService.finalizeResetPassword( token, cmd.password, cmd.name )
        if( !user ) {
            respond null, [status: HttpStatus.UNPROCESSABLE_ENTITY ]
            return
        }

        if( auth ) {
            def authToken = tokenGenerator.generateToken()
            springSecurityService.reauthenticate( user.email, cmd.password )
            authenticationTokenStorageService.storeToken( authToken, springSecurityService.principal )

            //Allow cross-domain to expose custom headers
            response.addHeader 'Access-Control-Expose-Headers', 'X-Auth-Token'
            response.addHeader 'X-Auth-Token', authToken
        }

        def link = g.createLink( controller: 'user', action: 'show', params: [id: user.id] )
        response.addHeader 'Location', link

        respondWithCacheHeaders user
    }

    @Secured(['permitAll'])
    def reset( ResetPasswordCommand cmd ) {
        def user = userService.findByEmail( cmd.email )
        if( !user ) {
            respond null, [ status: HttpStatus.NOT_FOUND ]
            return
        }

        //Initiate the password reset sequence for the specified user.
        userService.initiateResetPassword( user )
        respond null, [ status: HttpStatus.ACCEPTED ]
    }

    /**
     * Allow specifying "me" for the user id.
     * @param id
     * @return
     */
    protected User queryForResource( Serializable id ) {
        id == 'me' ? springSecurityService.currentUser as User : resource.get(id)
    }

}
