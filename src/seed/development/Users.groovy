/**
 * Created by ajbrown on 1/24/14.
 */
seed = {
    dependsOn = [ 'Roles', 'AdminAccounts' ]

    user( meta: [ key: 'email'],
        email: 'stacey@kibbler.org',
        name: 'Stacey Hensley',
        password: '123456',
        invitedBy: [ email: 'aj@ajbrown.org' ]
    )

    user( meta: [key: 'email'],
        email: 'aj+solo@ajbrown.org',
        name: 'A.J. (No Org)',
        password: '123456'
    )

    user( meta: [key: 'email'],
        email: 'aj+invited@ajbrown.org',
        name: 'A.J. (Invited)',
        password: '123456',
        invitedBy: [ email: 'aj@ajbrown.org' ]
    )
}
