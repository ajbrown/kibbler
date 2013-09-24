package kibbler

class ActiveOrganizationFilters {

    def organizationService
    def springSecurityService

    def boolean checkActiveOrg( session ) {
        def user = springSecurityService.currentUser as User

        if( !user.belongsTo( session.activeOrgId ) ) {

            log.warn "User ${user} attempted to access an organization they don't belong to"
            session.activeOrgId = null
            response.sendError( 403 )
            return false
        }
        return true
    }

    def filters = {
        pets(controller:'pets', action:'*' ) {
            before = {
                checkActiveOrg( session )
            }
        }

        people( controller: 'people', action: '*' ) {
            before = {
                checkActiveOrg( session )
            }
        }
    }
}
