package kibbler

import grails.converters.JSON

class PetsController {

    def springSecurityService
    def organizationService
    def petService

    def index() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def user = springSecurityService.currentUser as User

        //Get all pets for all organizations this user belongs to.
        //TODO we may want to only return for the currently active organization
        // but for now we're developing in single organization mode anyway.
        def pets = user.organizations.collectMany{ petService.readAllByOrg( it ) }

        withFormat{
            json{
                resp.data = pets
                render resp as JSON
            }
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
}


