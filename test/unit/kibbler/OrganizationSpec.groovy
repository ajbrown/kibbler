package kibbler

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.bson.types.ObjectId
import spock.lang.Specification

/**
 * Created by ajbrown on 7/23/13.
 */
@TestFor(Organization)
class OrganizationSpec extends Specification {

    def "membersAccounts returns the User's for all OrgRoles"() {
        def org = new Organization( name: 'Kibbler', id: new ObjectId() )
        def users = [
                new User( name: 'A.J.',     email: 'aj@kibbler.org',    id: new ObjectId() ),
                new User( name: 'Day Day',  email: 'david@kibbler.org', id: new ObjectId() )
        ]
        org.members = []

        when: 'Roles are added for users'

        org.members = users.collect{ new OrgRole( user: it, role: 'admin' ) }

        then: 'Those users will be returned with memberAccounts'

        org.memberAccounts.size() == users.size()
        org.memberAccounts.containsAll( users )
    }

}
