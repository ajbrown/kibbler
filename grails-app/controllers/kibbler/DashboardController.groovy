package kibbler

import grails.plugin.springsecurity.annotation.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class DashboardController {

    def index() {
        cache validFor: 60 * 15
    }

    /**
     * Renders angular views for the hero application
     * @return
     */
    def view() {
        def view = params.ngview

        render view: "/dashboard/${view}", layout: null
    }
}
