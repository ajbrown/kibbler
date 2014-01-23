package kibbler

import grails.converters.JSON


/**
 */
class Event {

    static {
        JSON.registerObjectMarshaller( EventType ) { it.toString() }
    }

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
        pet     index: 'idx_event_pet_date_created'
        person  index: 'idx_event_person_date_created'
        organization index: 'idx_event_organization_date_created'

        dateCreated index: 'idx_event_organization_date_created,idx_event_pet_date_created,idx_event_person_date_created'

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
