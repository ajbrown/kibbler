package kibbler

import grails.plugin.cache.Cacheable
import org.apache.commons.codec.language.Metaphone

class SuggestionService {

    private dogBreedSuggestions = []
    private catBreedSuggestions = []
    private nameSuggestions = []

    private metaphone = new Metaphone()
    private random = new Random()

    /**
     * Provides a list of suggested breed names based on the provided hint.  This can be used for autocompletion
     * purposes.  Returned breeds will be those that include the hint, or that are similar to the hint.
     * @param species
     * @param query
     * @return
     */
    @Cacheable('breed-suggestions')
    def List<String> suggestBreeds( Species species, String hint = '' ) {
        catBreedSuggestions = catBreedSuggestions ?: BreedSuggestion.findAllBySpecies( 'CAT' )
        dogBreedSuggestions = dogBreedSuggestions ?: BreedSuggestion.findAllBySpecies( 'DOG' )

        hint = hint?.trim() ?: ''


        def list = species == Species.DOG ? dogBreedSuggestions : catBreedSuggestions

        def breedMatcher = { BreedSuggestion breed ->

            def lower = breed.name.toLowerCase()
            def hintLower = hint.toLowerCase()
            //if the breed name is equal to the hint
            if( lower == hintLower ) {
                return [ weight: 10, name: breed.name ]
            }

            //if the breed name starts with the hint
            if( lower.startsWith( hintLower ) ) {
                return [ weight: 7, name: breed.name ]
            }

            //If the breed name sounds like the hint
            def hintMetaphone = metaphone.encode( hint )
            if( metaphone.isMetaphoneEqual( breed.metaphone, hintMetaphone ) ) {
                return [ weight: 5, name: breed.name ]
            }

            //If the breed contains the search phrase
            if( lower.contains( hintLower ) ) {
                return [ weight: 3, name: breed.name ]
            }

            return null
        }

        list.collect( breedMatcher )
                .findAll{ it }
                .sort{ a, b ->
                   b.weight <=> a.weight == 0 ? a.name <=> b.name : b.weight <=> a.weight
                }*.name
    }

    /**
     * Suggest a name based on the species and sex.
     * @param species
     * @param sex
     * @param num
     * @return
     */
    def String suggestName( String sex ) {
        sex = sex.toLowerCase()
        nameSuggestions[sex] = nameSuggestions[sex] ?: NameSuggestion.findAllBySex( sex )
        nameSuggestions[sex]?.getAt( random.nextInt( nameSuggestions[sex].size() + 1 ) )
    }
}
