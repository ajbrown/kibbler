package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON

class PeopleController {

    def springSecurityService
    def personService
    def organizationService
    def objectMapper = new ObjectMapper()

    def index() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def user = springSecurityService.currentUser as User
        def people = user.organizations.collectMany{ personService.readAllForOrg( it ) }

        withFormat{
            json{
                resp.data = people
                render resp as JSON
            }
        }
    }

    def update() {
        def person = personService.read( params.id )
        def user   = springSecurityService.currentUser as User
        def resp   = new JSONResponseEnvelope( status: 200 )

        if( !user.belongsTo( person.organization ) ) {
            response.sendError( 403, "You are not authorized to modify this person." )
        }

        def fields = objectMapper.readValue( request.inputStream, Map.class )
        def saved  = personService.updateFields( fields, person, user )

        if( !saved ) {
            resp.status = 400
            resp.errors = person.errors.allErrors
            resp.data = person
        } else {
            resp.data = saved
        }

        withFormat{
            json{ render resp as JSON }
        }
    }

    def create( CreatePersonCommand cmd) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def resp = new JSONResponseEnvelope( status: 200 )
        def org = organizationService.read( cmd.organizationId )

        if( !org ) {
            throw new Exception( 'The specified organization doesn\'t exist' )
        }

        if( !user.belongsTo( org ) ) {
            throw new Exception( "You don't belong to the specified organization." )
        }

        if( cmd.validate() ) {
            def person = new Person( name: cmd.name, adopter: cmd.adopter, foster: cmd.foster )
            if( personService.create( person, org, user ) ) {
                resp.data = person
            } else {
                resp.status = 400
                resp.errors.addAll person.errors.allErrors
            }

        } else {
            resp.status = 400
            resp.errors.addAll cmd.errors.allErrors
        }

        withFormat{
            json{
                response.status = resp.status
                render resp as JSON
            }
        }
    }

    def view() {
        def resp = JSONResponseEnvelope( status: 200 )
        def person = personService.read( params.id )

        if( !person ) {
            response.status = 404
        }

        def user = springSecurityService.currentUser as User

        //make sure the person belongs to one of the user's organizations
        if( ! user.belongsTo( person.organization ) ) {
            response.status = 403
        }

        withFormat{
            json{
                resp.data = person
                render resp as JSON
            }
        }
    }
}
