package bigbird

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class OrganizationController {

    def organizationService
    def springSecurityService

    def index() {

        def orgs = organizationService.

        withFormat {
            json {
                render orgs as JSON
            }
        }
    }

    def create() {
        def user = springSecurityService.currentUser as User
        def org = organizationService.createOrganization( params.name, user )

        withFormat {
            json {
                render org as JSON
            }
        }
    }
}
