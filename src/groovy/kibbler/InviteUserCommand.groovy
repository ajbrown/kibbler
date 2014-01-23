package kibbler

import grails.validation.Validateable

/**
 * Created by ajbrown on 7/28/13.
 */
@Validateable
class InviteUserCommand {
    String email
    String name

    static constraints = {
        email email: true, blank: false
    }
}
