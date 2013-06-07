package kibbler

import org.apache.commons.codec.language.Metaphone

class SuggestionService {


    private dogBreedSuggestions = []
    private catBreedSuggestions = []
    private dogNameSuggestions = [:]
    private catNameSuggestions = [:]

    private metaphone = new Metaphone()
    private random = new Random()


    /**
     * Populates the initial suggested breeds.
     * TODO this needs a better home.
     */
    def void populateBreedSuggestions() {
        def transform = { name -> [ metaphone: metaphone.encode( name ), name: name ] }

        dogBreedSuggestions = getDataFromListFile( 'dog_breeds.txt' ).collect transform
        catBreedSuggestions = getDataFromListFile( 'cat_breeds.txt' ).collect transform
    }

    /**
     * Populate our name suggestions
     * @return
     */
    def populateNameSuggestions() {
        Species.values().each{ species ->
            def list = species == Species.CAT ? catNameSuggestions : dogNameSuggestions
            ['male','female'].each{ sex ->
                list[ sex ] = getDataFromListFile( "${species}_names_${sex}.txt" )
            }
        }
    }


    /**
     * Provides a list of suggested breed names based on the provided hint.  This can be used for autocompletion
     * purposes.  Returned breeds will be those that include the hint, or that are similar to the hint.
     * @param species
     * @param query
     * @return
     */
    def List<String> suggestBreeds( Species species, String hint = '' ) {
        hint = hint?.trim() ?: ''
        def breedMatcher = { breed ->

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

        if( !dogBreedSuggestions || !catBreedSuggestions ) {
            populateBreedSuggestions()
        }

        def list = species == Species.DOG ? dogBreedSuggestions : catBreedSuggestions

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
    def String suggestName( Species species, String sex ) {
        if( !dogNameSuggestions || !catNameSuggestions ) {
            populateNameSuggestions()
        }
        def list = species == Species.CAT ? catNameSuggestions : dogNameSuggestions
        def r = random.nextInt( list[sex].size()+1 )
        list[ sex ][ r ]
    }


    /**
     * Load all of the data from a list file in our configuration.  Lines that are blank or are a comment will be
     * ignoerd.
     * @param filename
     * @return
     */
    def private getDataFromListFile( String filename ) {
        def lines = this.class.classLoader.getResourceAsStream(filename).readLines()

        lines.findAll{ !it.startsWith( "#" ) && it.trim() }.collect{ it.trim() }
    }

}
