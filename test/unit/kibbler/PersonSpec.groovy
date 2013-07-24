package kibbler

import grails.test.mixin.*
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Person)
@Mixin(GrailsUnitTestMixin)
class PersonSpec extends Specification {

    def person = new Person()

    def "setting as do not adopt removes person from adopters and fosters" () {

        when:

        person.adopter = true
        person.foster  = true
        person.doNotAdopt = false

        then:

        person.adopter
        person.foster

        when:

        person.doNotAdopt = true

        then:

        !person.adopter
        !person.foster
    }

    def "person cannot be set as foster when do not adopt is set"() {

        mockForConstraintsTests( Person, [person] )

        when:

        person.name = "Bruce"
        person.doNotAdopt = true
        person.foster = true
        person.adopter = true

        then:

        !person.validate()
        person.errors.getFieldErrorCount('foster') == 1
        person.errors.getFieldErrorCount('adopter') == 1
    }

}
