package kibbler.admin

import grails.plugins.springsecurity.Secured
import kibbler.Pet

@Secured(['ROLE_ADMIN'])
class PetsAdminController {

    static scaffold = Pet
}
