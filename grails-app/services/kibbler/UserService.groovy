package kibbler

import com.microtripit.mandrillapp.lutung.MandrillApi
import com.microtripit.mandrillapp.lutung.view.MandrillMessage
import com.microtripit.mandrillapp.lutung.view.MandrillTemplate
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CachePut
import grails.plugin.cache.Cacheable
import org.springframework.dao.DataAccessException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserService implements UserDetailsService {

    def mailService
    def MandrillApi mandrillApi
    def grailsApplication

    def grailsLinkGenerator

    def read( id ) {
        User.get( id )
    }

    /**
     * Activate a user.  Usually this is triggered after a user has clicked on an activation link and filled out
     * their information.
     *
     * @param user
     * @return
     */
    def activate( User user ) {
        user.activationCode = null
        user.activated = true
        user.enabled = true
        user.save( flush: true )
    }

    /**
     * Initiate the reset password sequence, and send the user a confirmation code.
     * @todo This does not support multiple confirmation codes to exist at once.  If a user spams the "resend" button, and
     * gets their codes out of order, they could be frustrated.
     *
     * @param user
     * @return
     */
    def initiateResetPassword( User user ) {
        user.activationCode = User.generateActivationCode()
        if( !save( user ) ) {
            return false
        }

        def resetLink = grailsLinkGenerator.link(
                controller: 'user',
                action: 'reset',
                params: [code: user.activationCode, email: user.email],
                absolute: true
        )

        //send the reset password verification email
        def message = new MandrillMessage()
        message.to = [ [ email: user.email, name: user.name ] as MandrillMessage.Recipient ]
        message.globalMergeVars = [
                new MandrillMessage.MergeVar( 'RESET_CODE', user.activationCode ),
                new MandrillMessage.MergeVar( 'RESET_LINK', resetLink )
        ]
        mandrillApi.messages().sendTemplate( "kibbler-forgot-password", null, message, true )

        user
    }

    /**
     * Complete the process of resetting a user's password, and notify the user that their password has been changed successfully
     * @param user
     * @return
     */
    def finalizeResetPassword( User user, String password ) {
        user.activationCode = null
        user.password = password
        user.activated = true

        if( !save( user ) ) {
            return false
        }

        def message = new MandrillMessage()
        message.to = [ [ email: user.email, name: user.name ] as MandrillMessage.Recipient ]
        mandrillApi.messages().sendTemplate( "kibbler-notify-password-reset", null, message, true )

        user
    }

    /**
     * Create a new user by email address.  If an organization is specified, they will be added to that org.
     * @param emailAddress
     * @param org
     */
    def create( String email, String name ) {
        email = email.toLowerCase()

        //make sure no user exists with the email
        if ( User.countByEmail( email ) > 0 ) {
            return null
        }

        def user = new User( name: name, email: email, activated: false )
        def saved = user.insert( flush: true )

        saved
    }

    def save( User user ) {
        user.save()
    }

    def findByEmail( String email ) {
        email?.trim() ? User.findByEmailLike( email.toLowerCase( ) ) : null
    }

    /**
     * Send an invitation to create an account to a user by email address.
     * @param email
     */
    def invite( String email, String name, User inviter ) {

        //Creating should only be a problem if the email address is already in use.

        def created = create( email, name )
        if( !created ) {
            return null
        }

        created.invitedBy = inviter
        created.activationCode = User.generateActivationCode()

        def saved = created.save( failOnError: true )
        if( !saved ) {
            throw new Exception( "Could not send invite to user ${email}: trouble saving activation code." )
        }

        def activationLink = grailsLinkGenerator.link(
                controller: "user", action: "activate",
                params: [ code: created.activationCode ],
                absolute: true
        )

        mailService.sendMail {
            from "support@kibbler.org"
            to created.email
            subject "${inviter.name} has invited you to join Kibbler!"
            body "You've been invited by ${inviter.name} to join kibbler.\n\n To activate your account, " +
                    "click the following url: \n\n ${activationLink}"
        }

        saved
    }

    /**
     * Spring security loadUserByUsername method, allowing spring security to use this service for authentication.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        def user = findByEmail( username )
        if( !user ) {
            throw new UsernameNotFoundException( "No user found with the email: ${username}" )
        }

        user
    }
}
