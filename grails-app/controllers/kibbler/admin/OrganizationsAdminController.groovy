package kibbler.admin

import grails.plugin.springsecurity.annotation.Secured
import kibbler.Organization


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
