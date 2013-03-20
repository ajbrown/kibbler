package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON

class PetsController {

    def springSecurityService
    def organizationService
    def petService
    def personService
    ObjectMapper objectMapper

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
        def pet = petService.read( params.id )


        withFormat{
            json{
                resp.data = pet
                render resp as JSON
            }
        }
    }

    def update() {
        def pet = petService.read( params.id )
        def user = springSecurityService.currentUser as User
        def resp = new JSONResponseEnvelope( status: 201 )

        //make sure the pet belongs to one of the user's organizations.
        if( !user.belongsTo( pet.organization ) ) {
            response.sendError( 403, "You're not authorized to access this resource." )
        }

        def fields = objectMapper.readValue( request.inputStream, Map.class )
        def saved = petService.updateFields( fields, pet, user )

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
            def pet = petService.read( params.id )
            def adopter = personService.read( cmd.adopter )

            //Make sure the pet exists
            if( !pet ) {
                //TODO return a 404 instead
                throw new Exception( 'Could not find the specified pet' )
            }

            //Make sure the adopter exists.
            if( !adopter ) {
                //TODO better exceptions
                throw new Exception( 'Adopter does not exist' )
            }

            if( !petService.adopt( pet, adopter, user ) ) {
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

    def foster( FosterPetCommand cmd ) {
        cmd.clearErrors()
        def user = springSecurityService.currentUser as User
        def jsonResponse = new JSONResponseEnvelope( status: 201 )

        if( cmd.validate() ) {
            def pet = petService.read( params.id )
            def foster = personService.read( cmd.personId )

            //Make sure the pet exists
            if( !pet ) {
                //TODO return a 404 instead
                throw new Exception( 'Could not find the specified pet' )
            }

            //Make sure the foster exists.
            if( !foster ) {
                //TODO better exceptions
                throw new Exception( 'Adopter does not exist' )
            }

            if( !petService.foster( pet, foster, user ) ) {
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
            def pet = petService.read( params.id )

            //Make sure the pet exists
            if( !pet ) {
                //TODO return a 404 instead
                throw new Exception( 'Could not find the specified pet' )
            }

            if( !petService.hold( pet, user ) ) {
                response.status = 500
                jsonResponse.errors = pet.errors?.allErrors
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
}


