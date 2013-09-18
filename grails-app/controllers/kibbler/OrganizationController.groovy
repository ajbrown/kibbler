package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class OrganizationController {

    def organizationService
    def springSecurityService
    def userService
    def eventService
    def petService
    def personService

    ObjectMapper objectMapper

    def beforeInterceptor = {
        params.orgId = session.activeOrgId

        def skipActions = ['index','create']

        if( params.action in skipActions ) {
            return true
        }

        def org = organizationService.read( params.id )

        if( !org ) {
            response.sendError( 404, 'The specified organization was not found.' )
            return false
        }

        if( !springSecurityService.currentUser.belongsTo( org ) ) {
            response.sendError( 403, "You do not have access to this organization." )
        }

        params.org = org
    }

    def index() {

        def user = springSecurityService.currentUser as User
        def orgs = user.organizations

        withFormat {
            html {
                return [ user: user, orgs: orgs ]
            }
            json {
                render orgs as JSON
            }
        }
    }

    def dashboard() {
        def org  = params.org as Organization
        def user = springSecurityService.currentUser as User

        def model = [ user: user, organization: org ]

        withFormat{
            html { return model }
            json { render model as JSON }
        }
    }

    def contract() {
        def org     = organizationService.
        def pet     = petService.read( params.pet )
        def person  = personService.read( params.person )


    }

    def read() {
        def resp = new JSONResponseEnvelope( status: 200 )

        withFormat{
            json{
                resp.data = params.org
                render resp as JSON
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

    def update() {
        def user = springSecurityService.currentUser as User
        def resp = new JSONResponseEnvelope( status: 201 )

        def fields = objectMapper.readValue( request.inputStream, Map.class )
        def saved = organizationService.updateFields( fields, params.org, user )

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

    def history() {
        def jsonResponse = new JSONResponseEnvelope( status: 200 )
        def events = eventService.listByOrganization( params.org, params.days ?: 30 )

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

    /**
     * Show all of the transactions for an organization
     * @return
     */
    def listTransactions() {
        def resp = new JSONResponseEnvelope( status: 200 )

        resp.data = organizationService.listTransactions( params.org )

        withFormat {
            json {
                resp.status = response.status
                render resp as JSON
            }
        }
    }

    def termsText() {
        def template = organizationService.readAdoptionContractTemplate( params.org )
        if( !template ) {
            response.status = 404
        }

        def termsText = ""
        def padding = template.terms.size() > 9 ? 2 : 1
        template.terms.eachWithIndex{ it, index ->
            termsText += "${(index+1).toString().padLeft(padding)}) ${it}\n\n"
        }

        render termsText
    }

    def addTransaction( AddTransactionCommand cmd ) {
        cmd.clearErrors()

        def resp = new JSONResponseEnvelope( status: 200 )
        def user = springSecurityService.currentUser as User

        //turn the transaction into a transaction record.
        def trans = new Transaction(
                amountCents: cmd.amount * 100,
                category: cmd.category,
                enteredBy: user,
                pet: cmd.pet ? petService.read( cmd.pet ) : null,
                organization: params.org
        )

        resp.data = organizationService.addTransaction( trans, user )
        if( !resp.data ) {
            response.status = 500
            resp.errors = trans.errors.allErrors
        }

        withFormat{
            json {
                resp.status = response.status
                render resp as JSON
            }
        }
    }
}
