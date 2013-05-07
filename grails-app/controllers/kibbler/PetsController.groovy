package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON

class PetsController {

    def organizationService
    def petService
    def personService
    def springSecurityService
    def eventService

    ObjectMapper objectMapper

    def beforeInterceptor = {
        def skipActions = ['index','create']

        if( params.action in skipActions ) {
            return true
        }

        def pet = petService.read( params.id )

        if( !pet ) {
            response.sendError( 404, 'The specified pet was not found.' )
            return false
        }

        if( !springSecurityService.currentUser.belongsTo( pet.organization ) ) {
            response.sendError( 403, "You do not have access to this pet." )
        }

        params.pet = pet
    }

    def index() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def user = springSecurityService.currentUser as User

        //Get all pets for all organizations this user belongs to.
        //TODO we may want to only return for the currently active organization
        // but for now we're developing in single organization mode anyway.
        def pets = user.organizations.collectMany{ petService.readAllForOrg( it ) }

        withFormat{
            json{
                resp.data = pets
                render resp as JSON
            }
        }
    }

    def read() {
        def resp = new JSONResponseEnvelope( status: 200 )

        withFormat{
            json{
                resp.data = params.pet
                render resp as JSON
            }
        }
    }

    def update() {
        def user = springSecurityService.currentUser as User
        def resp = new JSONResponseEnvelope( status: 201 )

        def fields = objectMapper.readValue( request.inputStream, Map.class )
        def saved = petService.updateFields( fields, params.pet, user )

        //TODO if saving failed due to optimistic locking, refresh and try again.

        if( !saved ) {
            resp.status = 400
            resp.errors = pet.errors.allErrors
            resp.data = pet
        } else {
            resp.data = saved
        }

        withFormat{
            json{ render resp as JSON }
        }
    }

    def create( CreatePetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        def org  = organizationService.read( cmd.organizationId )
        if( !org ) {
            throw new Exception( 'The specified organization doesn\'t exist' )
        }

        //make sure the organization belongs to the user
        if( !user.belongsTo( org ) ) {
            throw new Exception( "You don't belong to the specified organization." )
        }

        if( cmd.validate() ) {
            def pet = new Pet( givenName: cmd.name, sex: cmd.sex, type: cmd.species )

            if ( petService.create( org, pet, user ) ) {
                jsonResponse.status = 200
                jsonResponse.data = pet
            } else {
                jsonResponse.status = 400
                jsonResponse.errors.addAll pet.errors.allErrors
            }

        } else {
            jsonResponse.status = 400
            jsonResponse.errors.addAll cmd.errors.allErrors
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def adopt( AdoptPetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 201 )

        if( cmd.validate() ) {
            def adopter = personService.read( cmd.adopter )

            //Make sure the adopter exists.
            if( !adopter ) {
                //TODO better exceptions
                throw new Exception( 'Adopter does not exist' )
            }

            if( !petService.adopt( params.pet, adopter, user ) ) {
                response.status = 500
                jsonResponse.errors = params.pet.errors.allErrors
            }

            jsonResponse.data = params.pet
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def foster( FosterPetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 201 )

        if( cmd.validate() ) {
            def foster = personService.read( cmd.personId )

            //Make sure the foster exists.
            if( !foster ) {
                //TODO better exceptions
                throw new Exception( 'Adopter does not exist' )
            }

            if( !petService.foster( params.pet, foster, user ) ) {
                response.status = 500
                jsonResponse.errors = pet.errors.allErrors
            }

            jsonResponse.data = pet
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def hold( HoldPetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 201 )

        if( cmd.validate() ) {
            //Make sure the pet exists
            if( !params.pet ) {
                //TODO return a 404 instead
                throw new Exception( 'Could not find the specified pet' )
            }

            if( !petService.hold( params.pet, user ) ) {
                response.status = 500
                jsonResponse.errors = params.pet.errors?.allErrors
            }

            jsonResponse.data = params.pet
        }

        withFormat{
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def reclaim() {
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        def response = petService.reclaim( params.pet, user )

        jsonResponse.data = response

        withFormat {
            json{
                response.status = jsonResponse.status
                render jsonResponse as JSON
            }
        }
    }

    def history() {
        def jsonResponse = new JSONResponseEnvelope( status: 200 )

        def events = eventService.listByPet( params.pet, params.days ?: 30 )
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


