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

    def Person read( id ) {
        def key = id instanceof ObjectId ? id : new ObjectId( id )
        Person.read( key )
    }

    def readAllForOrg( Organization org ) {
        Person.createCriteria().list {
            eq "organization", org
            order "name"
        }
    }

    def updateFields( Map fields, Person person, User updater ) {
        fields.each{ key, value ->
            person[ key ] = value
        }
        person.lastUpdatedBy = updater
        person.save()
    }
}
