package bigbird

import grails.converters.JSON

class UserController {

    def springSecurityService

    def index() {
        def user = springSecurityService.getCurrentUser() as User

        withFormat{
            json {
                def data = [:]
                data.id = user.id.toString()
                data.email = user.email
                data.organizations = user.organizations

                render data as JSON
            }
            html { [ userInstance: user ] }
        }
    }

}
