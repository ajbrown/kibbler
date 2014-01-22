package kibbler.admin

import grails.plugin.springsecurity.annotation.Secured
import kibbler.Person

@Secured(['ROLE_ADMIN'])
class PersonsAdminController {

    static scaffold = Person

}
