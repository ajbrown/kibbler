package kibbler

import grails.plugin.spock.IntegrationSpec

/**
 * Created by ajbrown on 7/24/13.
 */
class PetServiceIntegrationSpec extends IntegrationSpec {

    def petService
    def User user
    def Organization org

    def setup() {
        Person.collection.drop()
        User.collection.drop()
        Organization.collection.drop()
        OrgRole.collection.drop()

        petService.eventService = Mock(EventService)

        org    = new Organization( name: 'Kibbler' )
        user   = new User( name: 'A.J.', email: 'aj@kibbler.org' ).save()
        org.addToMembers( new OrgRole( user: user, role: 'admin' ) )
        org.save()
    }

    def "pets can be created and read "() {
        def pet = new Pet( type: Species.DOG, name: 'Mikka', breed: 'Rott', sex: 'female', slug: 'd' )

        when:
        def result = petService.create( org, pet, user )

        then:
        result == pet
        result.organization == org
        result.name == 'Mikka'

        when:

        result = petService.read( pet.id.toString() )

        then:

        result == pet
    }

    def "all pets from an organization can be read"() {
        def badOrg = new Organization( name: 'Petland' ).save()
        def pets = [
                new Pet( type: Species.DOG, name: 'Mikka', sex: 'female', organization: org, slug: 'a' ),
                new Pet( type: Species.DOG, name: 'Dawson', sex: 'male',  organization: org, slug: 'b' ),
                new Pet( type: Species.DOG, name: 'Odie', sex: 'male', organization: org, slug: 'c' )
        ]

        def expect = pets.collect{ petService.create( org, it, user ) }
        def badPet = new Pet( type: Species.DOG, name: 'Vinnie', sex: 'male', organization: badOrg, slug: 'aa' )

        def notExpected = [ petService.create( badOrg, badPet, user ) ]


        when:
        def result = petService.readAllForOrg( org )

        then:
        result.size() == expect.size()
        result.containsAll( expect )

        when:
        result = petService.readAllForOrg( badOrg )

        then:
        result.size() == notExpected.size()
        result == notExpected

    }
}
