package kibbler

import org.bson.types.ObjectId

class PersonService {

    def create( Person person, Organization org, User creator = null ) {
        person.createdBy = creator
        person.organization = org
        def saved = person.save()
        if( saved ) {
            org.addToPeople( person )
            org.save()
        }

        saved
    }

    def read( id ) {
        def key = id instanceof ObjectId ? id : new ObjectId( id )
        Person.read( key )
    }

    def readAllForOrg( Organization org ) {
        org.people
    }
}
