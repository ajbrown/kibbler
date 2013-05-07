package kibbler

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.WriteConcern
import grails.converters.JSON
import org.bson.types.ObjectId

/**
 */
class Event {
    ObjectId id
    Organization organization
    Pet pet
    Person person
    EventType type
    User actor
    List args

    boolean hidden = false

    Date dateCreated

    static mapping = {
        pet index: true
        person index: true
        organization index: true
        dateCreated: index: true

        sort "dateCreated"
        order "desc"

        writeConcern WriteConcern.ERRORS_IGNORED
    }

    static constraints = {
        args nullable: true
        actor nullable: true
        pet nullable: true
        person nullable: true
    }

    def void setSubject( subject ) {
        switch( subject.class ) {
            case Organization:
                organization = subject
                break
            case Pet:
                pet = subject
                break
            case Person:
                person = subject
        }
        log.debug "subject: ${subject}"
    }
}
