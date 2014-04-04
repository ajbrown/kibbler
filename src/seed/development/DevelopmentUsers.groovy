/**
 * Created by ajbrown on 1/24/14.
 */
seed = {
    dependsOn( ['Roles' ] )

    user( meta: [ key: 'email', update: false],
            email: 'aj@ajbrown.org',
            name: 'A.J. Brown',
            password: '123456'
    )

    user( meta: [ key: 'email', update: false],
            email: 'david@dmichael.org',
            name: 'David Michael',
            password: '123456'
    )

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

    userRole( meta: [ key: [ 'user', 'role' ], update: false],
            user: [ email: 'aj@ajbrown.org' ],
            role: [ authority: 'ROLE_ADMIN' ]
    )

    userRole( meta: [ key: [ 'user', 'role' ], update: false],
            user: [ email: 'david@dmichael.org' ],
            role: [ authority: 'ROLE_ADMIN' ]
    )
}
