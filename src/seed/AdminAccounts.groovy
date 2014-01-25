/**
 * Created by ajbrown on 1/23/14.
 */
seed = {
    dependsOn = [ 'Roles' ]
    user( meta: [ key: 'email', update: false],
            email: 'aj@ajbrown.org',
            name: 'A.J. Brown',
            password: new Date().toString()
    )

    user( meta: [ key: 'email', update: false],
            email: 'david@dmichael.org',
            name: 'David Michael',
            password: new Date().toString()
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