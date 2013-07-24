package kibbler

import grails.plugin.spock.IntegrationSpec

/**
 * Created by ajbrown on 7/23/13.
 */
class PersonServiceIntegrationSpec extends IntegrationSpec {

    def personService

    def User user
    def Organization org

    def setup() {
        Person.collection.drop()
        User.collection.drop()
        Organization.collection.drop()
        OrgRole.collection.drop()

        personService.eventService = Mock(EventService)

        org    = new Organization( name: 'Kibbler' )
        user   = new User( name: 'A.J.', email: 'aj@kibbler.org')
    }

    def "create will create a new person within an organization"() {
        def person = new Person( name: 'Day Day', email: 'david@kibbler.org' )

        when:
        def result = personService.create( person, org, user )

        then:
        result
        result == person
        person.organization == org
        person.createdBy == user
        person in org.people
    }

    def "A person can be found for a user within an organization"() {
        def person = new Person( name: 'A.J.', email: 'aj@kibbler.org', linkedAccount: user, organization: org )
        person.save( flush: true )

        expect:
        personService.findForAccount( user, org ) == person
    }

    def "An existing person can be read"() {
        def person = new Person( name: 'A.J.', email: 'aj@kibbler.org', organization: org ).save()

        expect:
        person == personService.read( person.id )
    }

    def "All people in an organization can be read"() {
        def badOrg = new Organization( name: 'PetLand' ).save()
        def expected = [
                new Person( name: 'A.J.', email: 'aj@kibbler.org', organization: org ).save(),
                new Person( name: 'David', email: 'david@kibbler.org', organization: org ).save()
        ]

        def unexpected = new Person( name: 'Josh', email: 'josh@petland.com', organization: badOrg ).save( flush:  true )

        when:
        def results = personService.readAllForOrg( org )

        then:
        results.size() == expected.size()
        results.containsAll( expected )
        !results.contains( unexpected )

        when:

        results = personService.readAllForOrg( badOrg )

        then:
        results.size() == 1
        results == [unexpected]
    }

    def "A person must have an email in order to be added as a team member"() {

    }

    def "Making a person a team member will create an account and add them to the organization"() {
        def person = new Person( name: 'Rachel', email: 'rachel@kibbler.org', organization: org )

        when:
        def result = personService.makeTeamMember( person, user )

        then:
        person.linkedAccount instanceof User
        person.linkedAccount.email == person.email
        person.linkedAccount.belongsTo( org )
    }

}
