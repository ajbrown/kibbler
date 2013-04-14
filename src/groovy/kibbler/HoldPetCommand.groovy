package kibbler

import grails.validation.Validateable

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 3/19/13
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class HoldPetCommand {
    String organizationId
    String reason
}
