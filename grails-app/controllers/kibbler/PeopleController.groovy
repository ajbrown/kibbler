package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class PeopleController {

    def springSecurityService
    def personService
    def organizationService
    def eventService

    def objectMapper = new ObjectMapper()

    def beforeInterceptor = {

        def skipActions = ['index','create']

        if( params.action in skipActions ) {
            return true
        }

        def person = personService.read( params.id )
        if( !person ) {
            response.sendError( 404, 'The specified person was not found.' )
            return false
        }

        def user = springSecurityService.currentUser as User
        if( !user.belongsTo( person.organization ) ) {
            response.sendError( 403, "You do not have access to this params.person." )
        }

        params.person = person
    }

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
        def resp   = new JSONResponseEnvelope( status: 200 )

        def fields = objectMapper.readValue( request.inputStream, Map.class )
        def saved  = personService.updateFields( fields, params.person, springSecurityService.currentUser )

        if( !saved ) {
            resp.status = 400
            resp.errors = params.person.errors.allErrors
            resp.data = params.person
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

    def read() {
        lastModified params.person.lastUpdated
        def resp = new JSONResponseEnvelope( status: 200 )

        withFormat{
            json{
                resp.data = params.person
                render resp as JSON
            }
        }
    }

    def ban() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def banned = personService.ban( params.person, springSecurityService.currentUser )

        if( !banned ) {
            response.status = 500
        }

        withFormat{
            json{
                resp.data = banned
                render resp as JSON
            }
        }
    }

    def invite() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def invited = personService.makeTeamMember( params.person, springSecurityService.currentUser )
        if( !invited ) {
            response.status = 500
        }

        withFormat{
            json{
                resp.data = invited
                resp.status = response.status
                render resp as JSON
            }
        }
    }

    def history() {
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        def events = eventService.listByPerson( params.person, params.days ?: 30 )
        if( events ) {
            lastModified events.first().dateCreated
        }

        jsonResponse.data = events.collect{
            [ event: it, message: eventService.translateMessage( it, request.locale ) ]
        }

        withFormat {
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }
}
