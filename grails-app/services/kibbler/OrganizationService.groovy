package kibbler



class OrganizationService {

    static transactional = 'mongo'

    def eventService

    /**
     * Load an Organization.
     * @param id
     * @return
     */
    def Organization read( id ) {
        if( !id ) { return null }
        Organization.findById( id )
    }

    /**
     * Create a new organization.
     * @param name The name of the organization.
     * @param creator the user that is creating the organization.  They will be set as the administrator.
     * @return
     */
    def createOrganization( String name, User creator ) {
        def org = new Organization( name: name )
        org.addToMembers( new OrgRole( role: 'admin', user: creator ) )
        org.name = name
        org.createdBy = creator

        def saved = org.insert( failOnError: true )
        if( saved ) {
            //create a person for this organization, representing the current user
            def person = new Person( name: creator.name, email: creator.email )
            person.organization = org
            person.linkedAccount = creator
            person.save( failOnError: true )

            //Add the event
            eventService.create( EventType.ORG_CREATED, org, creator )
        }

        saved
    }

    def Organization updateFields( Map fields, Organization org, User updater = null ) {
        fields.each{ key, value -> org[ key ] = value }
        org.lastUpdatedBy = updater

        def saved = org.save( failOnError: true )
        if( saved ) {
            eventService.create( EventType.ORG_UPDATE, org, updater, [fields] )
        }

        saved
    }

    /**
     * Add a user to an existing organization.
     * @param org
     * @param addded
     * @param role
     * @param addedBy
     * @return
     */
    def void addUserToOrganization( Organization org, User added, User addedBy = null, String role = 'user' ) {
        def exists = org.members?.find{ it.user == added }

        def saved

        if( !exists ) {
            org.members = org.members ?: []
            org.addToMembers( new OrgRole( role: role, user: added, createdBy: addedBy ) )
            saved = org.save( failOnError: true )
        } else if( exists.role != role ) {
            exists.role = role
            saved = exists.save( failOnError: true )
        }

        if( saved && addedBy ) {
            eventService.create( EventType.ORG_ADD_PERSON, org, addedBy, [added])
        }
    }

    /**
     * Remove a user from an organization.
     *
     * @param org
     * @param removed
     * @param removedBy
     */
    def void removeUser( Organization org, User removed, User removedBy = null ) {
        def exists = org.members?.find{ it.user == removed }

        if( !exists ) {
            return
        }

        org.removeFromMembers( exists )
        def saved = org.save( failOnError: true )

        if( saved && removedBy ) {
            eventService.create( EventType.ORG_REMOVE_PERSON, org, removedBy, [removed] )
        }
    }

    /**
     *
     * Add a financial transaction.
     *
     * @param trx
     * @param recorder the person recording the transaction.  Note that this can different thatn the person that the
     * transaction belongs to.  If I enter a transaction on someone elses behalf, I am the recorder
     */
    def addTransaction( Transaction trx, User recorder = null ) {
        trx.createdBy = recorder
        def saved = trx.insert( failOnError: true )
        if( saved ) {
            //Determine the EventType based on the transaction amount, and whether or not it was tied to a pet.
            def et = trx.amountCents > 0 ? EventType.ORG_TRANSACTION_REVENUE : EventType.ORG_TRANSACTION_EXPENSE
            if( trx.pet ) { et = EventType.ORG_TRANSACTION_PET_EXPENSE }

            eventService.create( et, trx.organization, recorder )
        }

        saved
    }

    /**
     * List all transactions for an organization
     * @param org
     * @return
     */
    def listTransactions( Organization org, int limitDays = 30 ) {
        Transaction.withStatelessSession {
            Transaction.createCriteria().list{
                eq "organization", org
                if( limitDays >= 0 ) {
                    gte "dateCreated", (new Date().clearTime()) - limitDays
                }
                order "dateCreated", "desc"
            }
        }
    }

    def readAdoptionContractTemplate( Organization org ) {
        AdoptionContractTemplate.findByOrganization( org )
    }
}
