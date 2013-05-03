package kibbler

import org.bson.types.ObjectId

class PetService {

    def eventService

    def Pet create( Organization org, Pet pet, User creator = null ) {
        pet.organization = org
        pet.createdBy = creator
        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_ADD, pet, creator )
        }
        saved
    }

    def Pet read( String id ) {
        Pet.read( new ObjectId( id ) )
    }

    def readAllForOrg( Organization org ) {
        Pet.findAllByOrganization( org )
    }

    def updateFields( Map fields, Pet pet, User user ) {
        fields.each { key, value ->
            pet[ key ] = value
        }
        pet.lastUpdatedBy = user
        def saved = pet.save()

        if( saved ) {
            eventService.create( EventType.PET_UPDATE, pet, user, [fields] )
        }
        saved
    }

    /**
     * Adopt a pet to the specified adopter.
     * TODO only allow a pet to be adopted if they're in a valid state.
     * @param org
     * @param pet
     * @param adopter
     * @param creator
     */
    def adopt( Pet pet, Person adopter, User creator = null ) {

        def record = new AdoptionRecord( organization: pet.organization, pet: pet, adopter: adopter,  createdBy: creator )

        if( !record.save() ) {
            //TODO better exception handling
            throw new Exception( 'Could not create adoption record, aborting adoption' )
        }

        pet.adopter = adopter
        pet.foster = null
        pet.status = 'adopted'
        pet.lastUpdatedBy = creator

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_ADOPT, pet, creator, [adopter, record] )
        }
        saved
    }

    /**
     * Foster a pet to the specified foster.
     * @param org
     * @param pet
     * @param foster
     * @param creator
     * @return
     */
    def foster( Pet pet, Person foster, User creator = null ) {
        def record = new FosterRecord( pet: pet, foster: foster, createdBy: creator )
        if( !record.save() ) {
            //TODO better exception handling
            throw new Exception( 'Could not create fostering record, aborting fostering' )
        }

        pet.adopter = null
        pet.foster = foster
        pet.status = 'foster'
        pet.lastUpdatedBy = creator

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_FOSTER, pet, creator, [foster, record] )
        }
        saved
    }

    /**
     * Reclaim a pet, removing the pet from their current foster or adopter.
     * @param pet
     * @param updater
     * @return
     */
    def reclaim( Pet pet, User updater = null ) {

        pet.adopter = null
        pet.foster  = null
        pet.status  = 'available'
        pet.lastUpdatedBy = updater

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_RECLAIM, pet, updater )
        }
        saved
    }

    /**
     * Place the pet on hold, so it cannot be adopted or fostered.
     * @param pet
     * @param creator
     * @return
     */
    def hold( Pet pet, User creator = null ) {
        pet.adopter = null
        pet.foster  = null
        pet.status  = 'hold'
        pet.lastUpdatedBy = creator

        def saved = pet.save()
        if( saved ) {
            eventService.create( EventType.PET_HOLD, pet, creator )
        }
        saved
    }
}
