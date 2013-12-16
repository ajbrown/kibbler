package kibbler

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Created by ajbrown on 7/23/13.
 */
@TestFor(PersonService)
@Mock([Organization, Person, User])
class PersonServiceSpec extends Specification {

    def service = new PersonService()
    def user    = new User( name: 'A.J.', email: 'aj@kibbler.org', id: new ObjectId() )
    def org     = new Organization( name: 'Kibbler', id: new ObjectId() )

    def setup() {
        service.eventService        = Mock(EventService)
        service.organizationService = Mock(OrganizationService)
        service.userService         = Mock(UserService)
    }


    def "individual fields can be updated"() {
        def person = new Person( name: 'Adrian', id: new ObjectId() )

        when:
        def fields = [ email: 'adrian@kibbler.org' ]
        service.updateFields( fields, person, user )

        then:
        person.email == 'adrian@kibbler.org'
        person.name == 'Adrian'
        person.lastUpdatedBy == user

        when:
        fields = [ company: 'Kibbler', email: 'aj@kibbler.org' ]
        service.updateFields( fields, person, user )

        then:
        person.email == 'aj@kibbler.org'
        person.name == 'Adrian'
        person.company == 'Kibbler'

    }

    def "banning a user places them on do not adopt list"() {
        def person = new Person( name: 'Michael Vick', id: new ObjectId() )

        when:
        service.ban( person, user )

        then:
        person.doNotAdopt
        person.lastUpdatedBy == user
    }
}
