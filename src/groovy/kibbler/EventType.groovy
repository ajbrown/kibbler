package kibbler

/**
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 4/30/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
enum EventType {
    ORG_ADD_USER('events.organization.addUser'),
    PET_UPDATE('events.pet.update'),
    PET_ADOPT('events.pet.adopt'),
    PET_RECLAIM('events.pet.reclaim'),
    PET_FOSTER('events.pet.foster'),
    PET_ADD('events.pet.add'),
    PERSON_UPDATE('events.person.update'),
    PERSON_CREATE('events.person.create'),
    PERSON_REMOVE('events.person.remove'),
    PERSON_ADD_DONOTADOPT('events.person.addToDoNotAdopt'),
    PERSON_ADD_AVAILABLE('events.person.addAvailable'),
    PERSON_ADD_FOSTER('events.person.addFoster'),
    PERSON_ADD_ADOPTER('events.person.addFoster'),
    PERSON_REMOVE_DONOTADOPT('events.person.removeToDoNotAdopt'),
    PERSON_REMOVE_AVAILABLE('events.person.removeAvailable'),
    PERSON_REMOVE_FOSTER('events.person.removeFoster'),
    PERSON_REMOVE_ADOPTER('events.person.removeAdopter')

    String code

    EventType( String code ) {
        this.code = code
    }
}
