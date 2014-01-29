package kibbler

import grails.plugin.springsecurity.annotation.Secured

class NgController {

    @Secured(['permitAll'])
    def kibblerExternal() {
        render view: "external/${params.ngview}"
    }
}
