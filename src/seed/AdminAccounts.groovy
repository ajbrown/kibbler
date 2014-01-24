/**
 * Created by ajbrown on 1/23/14.
 */
seed = {
    dependsOn = [ 'Roles' ]
    user( meta: [ key: 'email', update: false],
            email: 'aj@ajbrown.org',
            name: 'A.J. Brown',
            password: new Date().toString(),
            roles: [[ authority: 'ROLE_USER', authority: 'ROLE_ADMIN']]
    )

    user( meta: [ key: 'email', update: false],
            email: 'david@dmichael.org',
            name: 'David Michael',
            password: new Date().toString(),
            roles: [[ authority: 'ROLE_USER', authority: 'ROLE_ADMIN']]
    )
}