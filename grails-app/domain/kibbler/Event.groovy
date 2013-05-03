package kibbler

import org.bson.types.ObjectId

/**
 */
class Event {
    ObjectId id

    def subject
    Organization organization
    EventType type
    User actor
    List args

    Date dateCreated

    static mapping = {
        type index: true
        sort "dateCreated"
        order "desc"
    }

    static constraints = {
        args nullable: true
        actor nullable: true
    }
}
