package kibbler.admin

import grails.plugins.springsecurity.Secured
import kibbler.User

@Secured(['ROLE_ADMIN'])
class UsersAdminController {

    static scaffold = User
}
