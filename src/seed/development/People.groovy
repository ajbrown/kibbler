/**
 * Created by ajbrown on 4/4/14.
 */
seed = {
    dependsOn( [ 'DevelopmentUsers', 'Organizations' ] )

    person( meta: [ key: ['name', 'organization'], update: false ],
            name: 'Stacey Hensley',
            email: 'cranberrystace@gmail.com',
            company: 'Kibbler, LLC',
            address: '1413 Arlington Drive\nFairborn, Ohio',
            phone: '(937) 555-1234',
            adopter: true, foster: true, available: false,

            linkedAccount: [ email: 'stacey@kibbler.org' ],
            createdBy: [ email: 'david@dmichael.org' ],
            organization: [ name: 'Kibbler' ]
    )

    person( meta: [ key: ['name', 'organization'], update: false ],
            name: 'David Michael',
            email: 'david@kibbler.org',
            company: 'Kibbler, LLC',
            address: '1413 Arlington Drive\nFairborn, Ohio',
            phone: '(937) 555-4332',
            adopter: false, foster: true, available: false,

            linkedAccount: [ email: 'david@dmichael.org' ],
            createdBy: [ email: 'aj@ajbrown.org' ],
            organization: [ name: 'Kibbler' ]
    )

    person( meta: [ key: ['name', 'organization'], update: false ],
            name: 'A.J. Brown',
            email: 'aj@kibbler.org',
            company: 'Kibbler, LLC',
            address: '8445 Indian Mound Drive\nHuber Heights, Ohio 45424',
            phone: '(937) 540-0099',
            adopter: true, foster: false, available: false,

            linkedAccount: [ email: 'aj@ajbrown.org' ],
            createdBy: [ email: 'aj@ajbrown.org' ],
            organization: [ name: 'Kibbler' ]
    )

    person( meta: [ key: ['name', 'organization'], update: false ],
            name: 'Rachel Brown',
            email: 'rachel.cool3@gmail.com',
            company: '',
            address: '8445 Indian Mound Drive\nHuber Heights, Ohio 45424',
            phone: '',
            adopter: true, foster: false, available: false,

            linkedAccount: null,
            createdBy: [ email: 'aj@ajbrown.org' ],
            organization: [ name: 'Kibbler' ]
    )

    person( meta: [ key: ['name', 'organization'], update: false ],
            name: 'Michael Vick',
            adopter: false, foster: false, available: false, doNotAdopt: true,

            linkedAccount: null,
            createdBy: [ email: 'stacey@kibbler.org' ],
            organization: [ name: 'Kibbler' ]
    )
}
