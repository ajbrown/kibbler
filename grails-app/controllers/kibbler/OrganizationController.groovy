package kibbler

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class OrganizationController {

    def organizationService
    def springSecurityService
    def userService
    def petService

    def index() {

        def user = springSecurityService.currentUser as User
        def orgs = organizationService.listUserOrganizations( user )

        withFormat {
            json {
                render orgs as JSON
            }
        }
    }

    def read() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def org  = organizationService.read( params.id )
        def user = springSecurityService.currentUser as User

        if( !org ) {
            response.status = 404
        }

        //make sure the person belongs to one of the user's organizations
        if( ! user.belongsTo( org ) ) {
            response.status = 403
        }


        withFormat{
            json{
                resp.data = org
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

    def listTransactions() {
        def resp = new JSONResponseEnvelope( status: 200 )
        def user = springSecurityService.currentUser as User
        def org  = organizationService.read( params.id )

        if( !org ) {
            response.status = 404
        }

        if( !user.belongsTo( org ) ) {
            throw new Exception( "User doesn't belong to this organization." )
        }

        resp.data = organizationService.listTransactions( org )

        withFormat {
            json {
                resp.status = response.status
                render resp as JSON
            }
        }
    }

    def termsText() {
        def org = organizationService.read( params.id )
        def template = organizationService.readAdoptionContractTemplate( org )
        if( !org || !template ) {
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
        def org  = organizationService.read( params.id )

        if( !org ) {
            response.status = 404
        }

        if( !user.belongsTo( org ) ) {
            throw new Exception( "User doesn't belong to this organization." )
        }

        //turn the transaction into a transaction record.
        def trans = new Transaction(
                amountCents: cmd.amount * 100,
                category: cmd.category,
                enteredBy: user,
                pet: cmd.pet ? petService.read( cmd.pet ) : null,
                organization: org
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
