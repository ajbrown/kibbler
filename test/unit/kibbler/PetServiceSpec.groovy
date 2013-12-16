package kibbler

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Created by ajbrown on 7/24/13.
 */
@TestFor(PetService)
@Mock([Pet, AdoptionRecord, FosterRecord, Organization])
class PetServiceSpec extends Specification {

    def service = new PetService()

    def org  = new Organization( name: 'Kibbler', id: new ObjectId() )
    def user = new User( name: 'A.J.', email: 'aj@kibbler.org', id: new ObjectId() )

    def setup() {
        org.members = [ new OrgRole( user: user, role: 'admin' ) ]

        service.eventService = Mock(EventService)
    }

    def "a pet belongs to the organization it was created for"() {
        def pet = new Pet( type: Species.DOG, name: 'Mikka', breed: 'Rott', sex: 'female', slug: 'mikka' )

        when:
        service.create( org, pet, user)

        then:
        pet.organization == org
        pet.createdBy    == user
    }

    def "individual fields can be updated with updateFields"() {
        def pet = new Pet( name: 'Mikka', breed: 'Rott', type: Species.DOG, sex: 'female', slug: 'mikka' )

        when:
        def fields = [ name: 'Odie', breed: 'Poodle', sex: 'male', microchipped: true, housebroken: true ]
        service.updateFields( fields, pet, user )

        then:
        pet.name  == 'Odie'
        pet.breed == 'Poodle'
        pet.sex   == 'male'
        pet.microchipped
        pet.housebroken

        when:
        fields = [ markings: 'Stripe', neutered: true, weight: 90 ]
        service.updateFields( fields, pet, user )

        then:
        pet.name  == 'Odie'
        pet.breed == 'Poodle'
        pet.sex   == 'male'
        pet.microchipped
        pet.housebroken
        pet.markings == 'Stripe'
        pet.neutered
        pet.weight == 90
    }

    def "a pet can be marked as adopted and then reclaimed"() {
        def pet = new Pet( name: 'Mikka', breed: 'Rottweiler', type: Species.DOG, organization: org, slug: 'mikka' )
        def adopter = new Person( name: 'David', organization: org )

        when:
        def result = service.adopt( pet, adopter, null, user )

        then:
        result == pet

        pet.adopter == adopter
        !pet.foster
        pet.status == 'adopted'
        pet.lastUpdatedBy == user

        when:
        service.reclaim( pet, user )

        then:
        !pet.adopter
        !pet.foster
        pet.status == 'available'

    }

    def "a pet can be marked as fostered and then reclaimed"() {
        def pet = new Pet( name: 'Mikka', breed: 'Rottweiler', type: Species.DOG, organization: org, slug: 'mikka' )
        def foster = new Person( name: 'David', organization: org )

        when:
        def result = service.foster( pet, foster, user )

        then:
        result == pet

        !pet.adopter
        pet.foster == foster
        pet.status == 'fostered'
        pet.lastUpdatedBy == user

        when:
        service.reclaim( pet, user )

        then:
        !pet.adopter
        !pet.foster
        pet.status == 'available'

    }

    def "a pet can be placed on hold"() {
        def pet = new Pet( name: 'Mikka', breed: 'Rottweiler', type: Species.DOG, organization: org, slug: 'mikka' )

        when:
        def result = service.hold( pet, user )

        then:
        result == pet

        !pet.adopter
        !pet.foster
        pet.status == 'hold'
        pet.lastUpdatedBy == user
    }
}
