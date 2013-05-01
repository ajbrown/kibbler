package kibbler

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 4/30/13
 * Time: 12:29 AM
 * To change this template use File | Settings | File Templates.
 */
class Event {
    Organization organization
    Person person
    Pet pet
    String messageCode
    List args

    Date dateCreated

    static mapping = {
        messageCode index: true
        sort "dateCreated"
        order "desc"
    }
}
