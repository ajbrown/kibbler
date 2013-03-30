package kibbler

import grails.validation.Validateable

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 3/28/13
 * Time: 12:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class AddTransactionCommand {
    String organizationId
    String category
    Double amount
    String pet
    String enteredBy
}
