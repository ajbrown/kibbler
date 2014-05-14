package kibbler

import grails.validation.Validateable

/**
 * Created by ajbrown on 9/7/13.
 */
@Validateable
class ActivateUserCommand {
    String code
    String name
    String password

    static constraints = {
        code validator: { val, obj, errors ->
            VerificationToken.countByToken( val ) ?: ['activationCode.doesNotExist']
        }
        password nullable: true
    }
}
