package kibbler

import grails.validation.Validateable

/**
 *
 */
@Validateable
class CreatePersonCommand {
    String organizationId
    String name
    Boolean adopter = false
    Boolean foster = false

    static constraints = {
        organizationId nullable: false
        name blank: false
    }
}
