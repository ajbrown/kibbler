package kibbler

import org.apache.commons.codec.language.Metaphone
import org.apache.commons.codec.language.Soundex

class NameSuggestion {

    String name
    String sex
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
        soundex     index: 'idx_name_suggestion_soundex'
        metaphone   index: 'idx_name_suggestion_metaphone'
    }

    static constraints = {
        sex inList: ['male','female']
        name unique: 'sex'
        soundex nullable: true
        metaphone nullable: true
    }
}
