package kibbler

import grails.validation.Validateable

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 3/16/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class FosterPetCommand {
    String fosterId

    static constraints = {
        fosterId nullable: false
    }
}
