package kibbler

import grails.validation.Validateable
import org.bson.types.ObjectId

/**
 *
 */
@Validateable
class CreatePetCommand {

    String orgId
    String givenName
    String sex
    Species species
    String breed

    static constraints = {
        orgId validator: { val, obj ->
            Organization.countById( new ObjectId( val ) ) > 0 ? true : [ 'organization.does.not.exist' ]
        }
        sex inList: ['male','female']
    }
}

