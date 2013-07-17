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

class UserService implements UserDetailsService {

    def read( id ) {
        def key = id instanceof ObjectId ? id : new ObjectId( id )
        User.read( key )
    }

    /**
     * Create a new user by email address.  If an organization is specified, they will be added to that org.
     * @param emailAddress
     * @param org
     */
    @CachePut(value='users-by-email', key='#email.toLowerCase()')
    def create( String email ) {
        email = email.toLowerCase()

        //make sure no user exists with the email
        if ( User.countByEmail( email ) > 0 ) {
            return null
        }

        def user = new User( email: email, activated: false )
        def saved = user.insert( flush: true )

        saved
    }

    @CacheEvict(value='users-by-email',key='#user.email.toLowerCase()')
    def save( User user ) {
        user.save()
    }

    @Cacheable('users-by-email')
    def findByEmail( String email ) {
        email?.trim() ? User.findByEmailLike( email.toLowerCase( ) ) : null
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
