package kibbler

import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class DashboardController {

    def index() {
        cache validFor: 60 * 15

        [
                createPet: new CreatePetCommand(),
                config: [
                ]
        ]
    }

    def config() {

    }
}
