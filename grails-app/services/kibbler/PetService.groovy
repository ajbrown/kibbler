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
}
