package kibbler.admin

import grails.plugins.springsecurity.Secured
import kibbler.InviteUserCommand
import kibbler.User

@Secured(['ROLE_ADMIN'])
class UsersAdminController {

    static scaffold = User

    def springSecurityService
    def userService

    def invite( InviteUserCommand cmd ) {
        def user = springSecurityService.principal as User
        userService.invite( cmd.email, cmd.name, user )
    }
}
