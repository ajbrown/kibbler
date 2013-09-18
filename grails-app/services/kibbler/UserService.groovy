package kibbler

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CachePut
import grails.plugin.cache.Cacheable
import org.bson.types.ObjectId
import org.springframework.dao.DataAccessException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.transaction.TransactionStatus

class UserService implements UserDetailsService {

    static transactional = 'mongo'

    def mailService

    def grailsLinkGenerator

    def read( id ) {
        def key = id instanceof ObjectId ? id : new ObjectId( id )
        User.read( key )
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
     * Create a new user by email address.  If an organization is specified, they will be added to that org.
     * @param emailAddress
     * @param org
     */
    @CachePut(value='users-by-email', key='#email.toLowerCase()')
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

    @CacheEvict(value='users-by-email',key='#user.email.toLowerCase()')
    def save( User user ) {
        user.save()
    }

    /**
     * Find a user with the specified activation code.
     * @param code
     * @return
     */
    def findByActivationCode( String code ) {
        User.findByActivationCode( code )
    }

    @Cacheable('users-by-email')
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
