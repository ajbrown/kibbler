package kibbler

import grails.validation.Validateable

/**
 * Created by ajbrown on 8/18/13.
 */
@Validateable
class AdoptionApplyCommand {
    String name
    String email
    String phone
    String petId
}
