package kibbler

import grails.validation.Validateable

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 2/14/13
 * Time: 12:26 AM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class CreatePetCommand {

    String organizationId
    String name
    String type

    static constraints = {
        type    inList: [ 'cat:female', 'cat:male', 'dog:female', 'dog:male' ]
    }

    def Species getSpecies() {
        if( !type ) {
            return null
        }
        def species = type.split(':')[0]
        switch( species ) {
            case 'cat':
                return Species.CAT
            case 'dog':
                return Species.DOG
        }
    }

    def String getSex() {
        def species = type.split(':')[1]

    }
}

