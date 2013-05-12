package kibbler

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CachePut
import grails.plugin.cache.Cacheable
import org.bson.types.ObjectId
import org.springframework.cache.CacheManager

class PetService {

    def eventService

    /**
     * Remove pets from the cache.  Allows referencing the object either by String id, ObjectId, or by Pet instance.
     * @param obj
     */
    static determineCacheKey( obj ) {
        def key
        if( obj instanceof ObjectId ) {
            key = obj.toString()
        } else if( obj instanceof String ) {
            key = obj
        } else if( obj instanceof Pet ) {
            key = obj.id.toString()
        }
        key
    }

    @Cacheable('pets')
    def Pet read( String id ) {
        Pet.read( new ObjectId( id ) )
    }

    def readAllForOrg( Organization org ) {
        Pet.findAllByOrganization( org )
    }

    @CacheEvict( value='pets', key='#root.args[1].id.toString()' )
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
    @CacheEvict( value='pets', key='#root.args[0].id.toString()' )
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
    @CacheEvict( value='pets', key='#root.args[0].id.toString()' )
    def foster( Pet pet, Person foster, User creator = null ) {
        def record = new FosterRecord( pet: pet, foster: foster, createdBy: creator )
        if( !record.save( failOnError: true ) ) {
            //TODO better exception handling
            throw new Exception( 'Could not create fostering record, aborting fostering' )
        }

        pet.adopter = null
        pet.foster = foster
        pet.status = 'fostered'
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
    @CacheEvict( value='pets', key='#root.args[0].id.toString()' )
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
    @CacheEvict( value='pets', key='#root.args[0].id.toString()' )
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
