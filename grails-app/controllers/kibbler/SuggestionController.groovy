package kibbler

import grails.converters.JSON

class SuggestionController {

    def suggestionService

    def breed( BreedSuggestionCommand cmd ) {
        def jsonResp = new JSONResponseEnvelope( status: 200 )
        def suggestions = suggestionService.suggestBreeds( cmd.species, cmd.query )

        withFormat{
            json{
                jsonResp.status = response.status
                jsonResp.data = suggestions
                render jsonResp as JSON
            }
        }
    }

    def name( NameSuggestionCommand cmd ) {
        def jsonResp = new JSONResponseEnvelope( status: 200 )
        def suggestion = suggestionService.suggestName( cmd.species, cmd.sex )

        withFormat{
            json{
                jsonResp.status = response.status
                jsonResp.data = suggestion
                render jsonResp as JSON
            }
        }
    }

}
