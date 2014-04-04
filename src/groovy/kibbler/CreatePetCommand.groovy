package kibbler

import grails.validation.Validateable


/**
 *
 */
@Validateable
class CreatePetCommand {

    String orgId
    String name
    String sex
    Pet.Species species
    String breed

    static constraints = {
        orgId validator: { val, obj ->
            Organization.countById( val ) > 0 ? true : [ 'organization.does.not.exist' ]
        }
        sex inList: ['male','female']
    }
}

