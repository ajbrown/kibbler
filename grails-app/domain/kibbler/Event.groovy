package kibbler


/**
 */
class Event {
    Organization organization
    Pet pet
    Person person
    EventType type
    User actor
    List<Object> args

    Boolean hidden = false

    Date dateCreated

    static mapping = {
        version false
        pet index: true
        person index: true
        organization index: true
        dateCreated: index: true

        sort dateCreated : 'desc'
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
