package kibbler

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Created by ajbrown on 7/23/13.
 */
@TestFor(OrganizationService)
@Mock([Organization, User, Person, Pet, OrgRole, Transaction])
class OrganizationServiceSpec extends Specification {

    def organizationService = new OrganizationService()
    def user = new User( id: new ObjectId(), name: 'A.J. Brown', email: 'aj@kibbler.org' )

    def setup() {
        organizationService.eventService = Mock(EventService)
    }

    def "read returns null if id is empty"() {
        expect:
        organizationService.read('') == null
    }


    def "individual fields can be updated using updateFields"() {
        def org = new Organization( name: 'Kibbler', id: new ObjectId() )
        def newUser = new User( email: 'david@kibbler.org', name: 'Dave', id: new ObjectId() )

        when:
        def fields = [ name: 'Kibbler New' ]
        def result = organizationService.updateFields( fields, org, user )

        then:
        result.name == 'Kibbler New'
        result.lastUpdatedBy == user

        when:

        fields = [adoptionFeeCents: 10000, description: 'test test' ]
        result = organizationService.updateFields( fields, org, newUser )

        then:

        result.name == 'Kibbler New'
        result.adoptionFeeCents == 10000
        result.description == 'test test'
        result.lastUpdatedBy == newUser
    }

    def "transactions can be added"() {
        def org = new Organization( name: 'Kibbler', id: new ObjectId() )

        when:

        def trx = new Transaction( category: 'vet', amountCents: 5000, organization: org )
        def result = organizationService.addTransaction( trx, user )

        then:

        result
    }
}
