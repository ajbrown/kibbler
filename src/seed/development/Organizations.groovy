/**
 * Created by ajbrown on 1/24/14.
 */

seed = {
    dependsOn( [ 'DevelopmentUsers' ] )

    organization( meta: [ key: 'name', update: false ],
        name: 'Kibbler',
        createdBy: [ email: 'aj@ajbrown.org' ]
    )

    orgRole( meta: [ key: ['user','organization'], update: false],
            user: [email: 'aj@ajbrown.org'], organization: [ name: 'Kibbler'], role: 'admin'
    )
    orgRole( meta: [ key: ['user','organization'], update: false],
            user: [email: 'david@dmichael.org'], organization: [ name: 'Kibbler'], role: 'admin'
    )
    orgRole( meta: [ key: ['user','organization'], update: false],
            user: [email: 'stacey@kibbler.org'], organization: [ name: 'Kibbler' ], role: 'member'
    )

    def loremIpsum = [

            'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                    'mauris non pulvinar ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                    'Pellentesque tincidunt, sapien eget\n' +
                    'porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus ante.',

            'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                    'mauris non pulvinar' +
                    'ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                    'Pellentesque tincidunt, sapien eget.',

            'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                    'mauris non pulvinar' +
                    'ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                    'Pellentesque tincidunt, sapien eget\n' +
                    'porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus.',

            'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                    'mauris non pulvinar' +
                    'ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                    'Pellentesque tincidunt, sapien eget\n' +
                    'porttitor rhoncus, elit eros pulvinar mauris.',

    ]

    contractTemplate( meta: [ key: 'organization', update: false ],
        organization: [ name: 'Kibbler' ], terms: loremIpsum, createdBy: [ email: 'aj@ajbrown.org' ]
    )
}