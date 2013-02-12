package bigbird

import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class DashboardController {

    def index() {}
}
