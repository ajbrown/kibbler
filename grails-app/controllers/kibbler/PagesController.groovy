package kibbler

class PagesController {

    def petService

    def organization() {

    }

    def pet() {
        def pet = petService.findBySlug( params.petSlug )

        [ pet: pet ]
    }
}
