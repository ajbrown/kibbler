package kibbler

import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional
class AuthenticationTokenStorageService implements TokenStorageService {

    def grailsCacheManager
    def userDetailsService

    /**
     * Get the username for a token.  For CiteStack, authentication tokens are always stored with the same level of
     * security as user passwords, so the input token needs to be hashed accordingly before the lookup is performed.
     *
     * Token lookups are cached, and a token that's expired based on the configuration will be treated like a
     * non-existing token.
     *
     * @param tokenValue
     * @return
     * @throws com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenNotFoundException
     */
    @Override
    Object loadUserByToken( String tokenValue ) throws TokenNotFoundException {
        def token = findTokenByPlainTextValue( tokenValue )
        if( !token ) {
            throw new TokenNotFoundException( "No token with the value: ${tokenValue} could be found." )
        }

        //A token that's passed it's expiration time is the same as one that doesn't exist.
        def expiryMinutes = SpringSecurityUtils.securityConfig.rest.token.expiry as Integer

        if( expiryMinutes ) {
            use(TimeCategory) {
                if( (token.dateCreated + expiryMinutes.minutes) <= new Date() ) {
                    throw new TokenNotFoundException( "Token with the value: ${tokenValue} has expired." )
                }

            }
        }

        userDetailsService.loadUserByUsername( token?.username )
    }

    /**
     * Store a token for a user, and update the token cache as necessary.
     *
     * @param tokenValue
     * @param principal
     */
    @Override
    void storeToken( String tokenValue, Object principal ) {
        def token = new AuthenticationToken( tokenValue: tokenValue, username: principal.username ).save()
        if( token ) {
            grailsCacheManager?.getCache( 'auth-token' )?.put( tokenValue, token )
        }
    }

    /**
     * Remove a token, and update the token cache.
     *
     * @param tokenValue
     * @throws TokenNotFoundException
     */
    @Override
    @CacheEvict( value = 'auth-token', key = '#tokenValue' )
    void removeToken( String tokenValue ) throws TokenNotFoundException {
        def token = findTokenByPlainTextValue( tokenValue )
        token?.delete( flush: true )
    }

    /**
     * Find a token by the plainText version of it's tokenValue.  AuthTokens are stored with the same level of
     * encryption as user passwords, so the plainTextValue needs to be hashed using the same mechanism first.
     *
     * Note that the results of this are cached, and the token may not actually exist.
     * @param plainTextValue
     */
    @Cacheable( value = 'auth-token', key = '#plainTextValue' )
    def findTokenByPlainTextValue( String plainTextValue ) {
        AuthenticationToken.findByTokenValue( plainTextValue.encodeAsSHA256() )
    }

}
