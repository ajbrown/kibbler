package kibbler.request

import grails.validation.Validateable

/**
 * Created by ajbrown on 1/25/14.
 */
@Validateable
class ResetPassword {
    String code
    String email
    String password
}
