package kibbler.admin

import grails.plugins.springsecurity.Secured
import kibbler.Organization
import org.bson.types.ObjectId

@Secured(['ROLE_ADMIN'])
class OrganizationsAdminController {

    def organizationService
    static scaffold = Organization

    def show() {
        def org = organizationService.read( params.id )
        if( !org ) {
            flash.message = "No Organization found with id ${params.id}"
            redirect action: "list"
        }
        [ organizationInstance: org ]
    }

    def edit() {
        def org = organizationService.read( params.id )
        if( !org ) {
            flash.message = "No Organization found with id ${params.id}"
            redirect action: "list"
        }
        [ organizationInstance: org ]
    }
}
