package kibbler

/**
 * EventTypes are categories of events stored as an Event object.
 */
enum EventType {
    ORG_ADD_PERSON('events.organization.addUser'),
    ORG_CREATED('events.organization.create'),
    ORG_TRANSACTION_REVENUE('events.organization.addRevenue'),
    ORG_TRANSACTION_EXPENSE('events.organization.addExpense'),
    ORG_TRANSACTION_PET_EXPENSE('events.organization.addPetExpense'),
    PET_UPDATE('events.pet.update'),
    PET_ADOPT('events.pet.adopt'),
    PET_RECLAIM('events.pet.reclaim'),
    PET_FOSTER('events.pet.foster'),
    PET_ADD('events.pet.add'),
    PET_HOLD('events.pet.hold'),
    PET_ADD_PHOTO('events.pet.photos'),
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
