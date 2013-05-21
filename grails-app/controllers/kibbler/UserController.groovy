package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class UserController {

    def springSecurityService

    def index() {
        def user = springSecurityService.getCurrentUser() as User

        withFormat{
            json {
                def data = [:]
                data.id = user.id.toString()
                data.email = user.email
                data.organizations = user.organizations.collect{ [ id: it.id.toString(), name: it.name ] }
                render data as JSON
            }
        }
    }

}
