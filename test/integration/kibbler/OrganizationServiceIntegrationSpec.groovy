package kibbler

import grails.plugin.spock.IntegrationSpec

/**
 * Created by ajbrown on 7/23/13.
 */

class OrganizationServiceIntegrationSpec extends IntegrationSpec {
    def organizationService


    def setup() {
        //Clean up the database
        User.collection.drop()
        Organization.collection.drop()
        OrgRole.collection.drop()
        Person.collection.drop()
        Transaction.collection.drop()
    }

    def "read will return an existing Organization"() {
        def organization = new Organization( name: 'Kibbler' )
        organization.save( flush: true )

        expect:
        organizationService.read( organization.id.toString() ) == organization
    }

    def "a new organization can be created by a user"() {
        def user = new User( name: 'A.J.', email: 'aj@kibblerapp.com' )

        when:

        def result = organizationService.createOrganization( orgName, user )

        then: 'An organization is created with '

        result?.name == orgName

        and: 'The creator has admin membership'
        result.members.size() == 1
        result.members.find{ it.role == 'admin' && it.user == user }

        and: 'A person is created for the creator of the organization, linked to their account'
        Person.findByLinkedAccountAndOrganization( user, result )


        where:

        orgName = 'Kibbler'
    }

    def "users can be added and removed from an org, and existing ones can be updated"() {
        def user    = new User( email: 'aj@kibblerapp.com', name: 'A.J.' )
        def newUser = new User( email: 'david@kibblerapp.com', name: 'Day Day' )
        def org     = new Organization( name: 'Kibbler' )

        expect: 'The user doesnt exist as a member of the organization'
        !org.members.find{ it.user == newUser }

        when: 'Using addUserToOrganization to add the user'
        organizationService.addUserToOrganization( org, newUser, user, 'viewer' )

        then: 'The user is now a part of the organization'

        org.memberAccounts.size() == 1
        org.members.size() == 1
        newUser in org.memberAccounts
        org.members.find{ it.user == newUser && it.role == 'viewer' }

        when: 'Using addUserToOrganization to update the users role'
        organizationService.addUserToOrganization( org, newUser, user, 'admin' )

        then: 'The users role is updated without adding a new role'

        org.memberAccounts.size() == 1
        org.members.find{ it.user == newUser && it.role == 'admin' }

        when: 'Using addUserToOrganization to remove the user'
        organizationService.removeUser( org, newUser, user )

        then: 'The user is no longer a member of the organization'
        !org.members?.find{ it.user == newUser }
        org.memberAccounts.size() == 0
    }

    def "transactions can be added and listed"() {
        def user    = new User( email: 'aj@kibblerapp.com', name: 'A.J.' )
        def org     = new Organization( name: 'kibbler' )
        org.addToMembers( new OrgRole( user: user, role: 'admin' ) )
        org.save( flush: true )

        when:

        def trx = new Transaction( category: 'vet', amountCents: 5000, organization: org )
        def result = organizationService.addTransaction( trx, user )

        then:

        result

        when:
        def listResult = organizationService.listTransactions( org )

        then:

        listResult.size() == 1
        result.id in listResult*.id
    }
}
