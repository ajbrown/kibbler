package kibbler

import grails.plugin.restfulCacheHeaders.CacheableRestfulController
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class VerificationTokenController extends CacheableRestfulController<VerificationToken> {

    VerificationTokenController() {
        super( VerificationToken, true )
    }

    @Secured(['ROLE_ADMIN'])
    def index( Integer max ) {
        super.index( max )
    }

    def show( String id ) {
        //In this case, "id" is actually the generated value.
        respondWithCacheHeaders VerificationToken.findByToken( id )
    }

}
