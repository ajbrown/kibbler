package kibbler.admin

import grails.plugins.springsecurity.Secured
import kibbler.Person

@Secured(['ROLE_ADMIN'])
class PersonsAdminController {

    static scaffold = Person

}
