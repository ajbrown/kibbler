package kibbler

import grails.validation.Validateable

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 6/5/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class NameSuggestionCommand {
    Species species
    String sex
}
