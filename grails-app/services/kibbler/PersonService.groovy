package kibbler

import org.bson.types.ObjectId

class PersonService {

    def userService
    def organizationService
    def eventService

    /**
     * Create a new person.
     * @param person
     * @param org
     * @param creator
     * @return
     */
    def create( Person person, Organization org, User creator = null ) {
        person.createdBy = creator
        person.organization = org
        def saved = person.save()
        if( saved ) {
            org.addToPeople( person )
            org.save()

            eventService.create( EventType.PERSON_CREATE, person, creator )
        }

        saved
    }

    /**
     * Read a single person by ID.
     * @param id
     * @return
     */
    def Person read( id ) {
        def key = id instanceof ObjectId ? id : new ObjectId( id )
        Person.read( key )
    }

    /**
     * Read all people in an organization.
     * @param org
     * @return
     */
    def readAllForOrg( Organization org ) {
        Person.createCriteria().list {
            eq "organization", org
            order "name"
        }
    }

    /**
     * Update the specified fields of a person.
     * @param fields
     * @param person
     * @param updater
     * @return
     */
    def updateFields( Map fields, Person person, User updater ) {
        fields.each{ key, value ->
            person[ key ] = value
        }

        person.lastUpdatedBy = updater
        def saved = person.save()
        if( saved ) {
            eventService.create( EventType.PERSON_UPDATE, person, updater )
        }
        saved
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

        def saved = person.save()
        if( saved ) {
            eventService.create( EventType.PERSON_ADD_DONOTADOPT, person, bannedBy )
        }
        saved
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
        def saved = person.save()
        if( saved ) {
            eventService.create( EventType.ORG_ADD_PERSON, person, granter )
        }

        saved
    }
}
