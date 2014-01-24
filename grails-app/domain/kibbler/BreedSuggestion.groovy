package kibbler

import org.apache.commons.codec.language.Metaphone
import org.apache.commons.codec.language.Soundex

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 6/5/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
class BreedSuggestion {

    String species
    String name
    String soundex
    String metaphone
    Set<String> hints
    Integer weight = 1

    def beforeInsert() {
        metaphone = new Metaphone().encode( name )
        soundex = new Soundex().encode( name )
    }

    def beforeUpdate() {
        if( isDirty('name') ) {
            metaphone = new Metaphone().encode( name )
            soundex   = new Soundex().encode( name )
        }
    }

    static mapping = {
        cache true
        soundex index: 'idx_suggestion_soundex_weight'
        weight  index: 'idx_suggestion_soundex_weight'
    }

    static constraints = {
        name unique: 'species'
        species inList: [ 'DOG', 'CAT' ]
        metaphone nullable: true
        soundex nullable: true
    }
}
