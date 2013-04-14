package kibbler

class DocumentsController {

    def organizationService
    def documentService

    /**
     * Make sure that the organization exists, and the current user belongs to the organization
     */
    def beforeInterceptor = {
        def user = currentUser() as User
        params.organization = organizationService.read( params.orgId )
        if( ! params.organization ) {
            response.status = 404
            return false
        }

        if( ! user.belongsTo( params.organization ) ) {
            response.status = 403
            return false
        }

        return true
    }

    def contract() {
        def org = params.organization as Organization
        def template =

        [
                organization: org
        ]
    }
}
