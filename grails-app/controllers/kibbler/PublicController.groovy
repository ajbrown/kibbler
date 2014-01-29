package kibbler

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PublicController {

    /**
     * Serve up the html for the public face of the application.
     * @return
     */
    def index() {
        render view: '/index'
    }

    /**
     * Renders angular views for the public application
     * @return
     */
    def view() {
        def view = params.ngview

        render view: "/public/${view}", layout: null
    }
}
