package kibbler

class PagesController {

    def petService

    def organization() {

    }

    def pets() {
        def pet = petService.findBySlug( params.slug )

        [ pet: pet ]
    }
}
