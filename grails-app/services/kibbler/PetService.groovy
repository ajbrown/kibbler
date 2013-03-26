package kibbler

import org.bson.types.ObjectId

class PetService {

    def Pet create( Organization org, Pet pet, User creator = null ) {
        pet.organization = org
        pet.createdBy = creator
        pet.save()
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
        pet.save()
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
        pet.save()
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
        pet.save()
    }

    def reclaim( Pet pet, User updater = null ) {
        pet.adopter = null
        pet.foster  = null
        pet.status  = 'available'
        pet.lastUpdatedBy = updater
        pet.save()
    }

    def hold( Pet pet, User creator = null ) {
        pet.adopter = null
        pet.foster  = null
        pet.status  = 'hold'
        pet.lastUpdatedBy = creator
        pet.save()
    }


}
