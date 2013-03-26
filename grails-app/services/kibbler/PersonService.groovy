package kibbler

import org.bson.types.ObjectId

class PersonService {

    def userService
    def organizationService

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

    /**
     * Banning is placing a person on the organization's "DO NOT ADOPT" list
     * @param person
     * @param bannedBy
     * @return
     */
    def ban( Person person, User bannedBy = null ) {
        person.doNotAdopt = true
        person.lastUpdatedBy = bannedBy
        person.save()
    }

    /**
     * Make the person into a team-member.  The person must have an email-address assigned first.  If a user exists
     * with the email of the person, they will be sent an invite to join.  If they do not already have an account,
     * a non-activated account will be created for them, and they will be sent a setup email.
     *
     * @param person
     * @param organization
     * @param granter the person that is making this user the team
     */
    def makeTeamMember( Person person, User granter = null ) {
        def user = userService.findByEmail( person.email )
        if( !user ) {
            //Create an account, which sends an invite email
            user = userService.create( person.email )
        }

        person.linkedAccount = user
        organizationService.addUserToOrganization( person.organization, user, granter )
        person.save()
    }
}
