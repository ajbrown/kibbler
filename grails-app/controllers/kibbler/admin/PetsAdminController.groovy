package kibbler.admin

import grails.plugins.springsecurity.Secured
import kibbler.Pet

@Secured(['ROLE_ADMIN'])
class PetsAdminController {

    static scaffold = Pet

    def petService

    def show() {
        def pet = petService.read( params.id )
        if( !pet ) {
            flash.message "No pet found with ID ${params.id}"
            redirect action: "list"
        }

        [ petInstance: pet ]
    }

    def edit() {
        def pet = petService.read( params.id )
        if( !pet ) {
            flash.message "No pet found with ID ${params.id}"
            redirect action: "list"
        }

        [ petInstance: pet ]
    }
}
