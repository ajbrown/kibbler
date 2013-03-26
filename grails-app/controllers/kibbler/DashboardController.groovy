package kibbler

import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class DashboardController {

    def index() {

        [
                createPet: new CreatePetCommand(),
                config: [
                ]
        ]
    }

    def config() {

    }
}
