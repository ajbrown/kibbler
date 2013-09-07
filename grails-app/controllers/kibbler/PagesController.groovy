package kibbler

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.springframework.context.MessageSourceResolvable
import org.springframework.context.i18n.LocaleContextHolder

@Secured('IS_AUTHENTICATED_ANONYMOUSLY')
class PagesController {

    def petService

    /**
     * TODO this belongs in a parent class (or similar) so we can use it in other controllers as well.
     */
    private final errorParser = {
        if( !it ) { return null }

        if( it instanceof MessageSourceResolvable ) {
            return grailsApplication.mainContext.getMessage( it, LocaleContextHolder.locale )
        } else {
            return it
        }
    }.memoize()

    def organization() {

    }

    def pets() {
        def pet = petService.findBySlug( params.slug )

        [ pet: pet ]
    }

    def apply( AdoptionApplyCommand cmd ) {
        def resp = new JSONResponseEnvelope( status: 200 )

        def pet = petService.read( cmd.petId )
        def app = petService.submitApplication( pet, cmd )

        if( app ) {
            resp.status = 201
            resp.data = app
        } else {
            resp.status = 400
            resp.errors = cmd.errors.fieldErrors.collect errorParser
        }

        withFormat{
            json {
                response.status = resp.status
                render resp as JSON
            }

            html {
                if( resp.isSuccess() ) {
                    flash.message = 'Your application has been submitted.  The organization will review your information and be in touch with you soon.'
                } else {
                    flash.message = 'There was an error submitting your application.  Please try again.'
                }
                redirect controller: 'pages', action: 'pets', params: [ slug: pet?.slug ]

            }
        }
    }
}
