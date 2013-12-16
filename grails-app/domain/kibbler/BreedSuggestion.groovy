package kibbler

import org.apache.commons.codec.language.Soundex

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 6/5/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
class BreedSuggestion {

    Species species
    String name
    String soundex
    Set<String> hints
    Integer weight = 1

    def beforeInsert() {
        soundex = new Soundex().encode( name )
    }

    static constraints = {
    }
}
