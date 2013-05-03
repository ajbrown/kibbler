package kibbler

import grails.plugin.cache.Cacheable
import org.bson.types.ObjectId

class UserService {

    def read( id ) {
        def key = id instanceof ObjectId ? id : new ObjectId( id )
        User.read( id )
    }

    /**
     * Create a new user by email address.  If an organization is specified, they will be added to that org.
     * @param emailAddress
     * @param org
     */
    def create( String email ) {
        email = email.toLowerCase()

        //make sure no user exists with the email
        if ( User.countByEmail( email ) > 0 ) {
            return null
        }

        def user = new User( email: email, activated: false )
        def saved = user.save( flush: true )

        saved
    }

    def findByEmail( String email ) {
        email?.trim() ? User.findByEmailLike( email.toLowerCase( ) ) : null
    }
}
