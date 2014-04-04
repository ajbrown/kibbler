/**
 * Created by ajbrown on 4/3/14.
 */

seed = {
    dependsOn = [ 'Organizations', 'DevelopmentUsers' ]

    pet( meta: [ key: 'name', update: false ],
            name: 'Mikka', species: 'DOG', breed: 'Rottweiler', gender: 'female',
            status: 'HOLD',
            age: 4, weight: 80, color: 'black/tan', neutered: false, createdBy: [ email: 'aj@ajbrown.org' ],
            notes: [
              [ author: [ email: 'aj@ajbrown.org'], content: 'Transferred from stacey to me.' ]
            ],
            organization: [ name: 'Kibbler' ]
    )

    pet( meta: [ key: 'name', update: false ],
            name: 'Dawson', species: 'DOG', breed: 'Yorkshire Terrier', gender: 'male',
            age: 8, weight: 11, color: 'tan', neutered: true, createdBy: [ email: 'aj@ajbrown.org' ],
            organization: [ name: 'Kibbler' ]
    )

    pet( meta: [ key: 'name', update: false ],
            name: 'Odie', species: 'DOG', breed: 'Poodle (Mix)', gender: 'male',
            age: 10, weight: 18, color: 'tan', neutered: true, housebroken: true, heartworm: true, microchiped: true,
            microchipId: '01-1234567',
            createdBy: [ email: 'aj@ajbrown.org' ], organization: [ name: 'Kibbler' ]
    )

    pet( meta: [ key: 'name', update: false ],
            name: 'Vinnie', species: 'DOG', breed: 'Mastiff', gender: 'male',
            age: 7, weight: 120, color: 'tan / black', neutered: true, housebroken: true, heartworm: true,
            microchiped: true, microchipId: '01-343452', specialNeeds: true,
            notes: [
                    [
                        author: [ email: 'stacey@kibbler.org' ],
                        content: 'Needs a steroid shot daily to suppress allergy symptoms'
                    ],
                    [
                        author: [ email: 'david@dmichael.org'],
                        content: 'Knows how to use doorknobs to open doors.  Should use deadbolts when containing.'
                    ]
            ],
            createdBy: [ email: 'david@dmichael.org' ], organization: [ name: 'Kibbler' ]
    )

    pet( meta: [ key: 'name', update: false ],
            name: 'Jack', species: 'DOG', breed: null, gender: 'male',
            age: 12, weight: 25, color: 'black', neutered: true, housebroken: true, heartworm: true,
            microchiped: false, specialNeeds: true,
            notes: [
                    [
                            author: [ email: 'stacey@kibbler.org' ],
                            content: 'Has had back surgery previously.'
                    ],
                    [
                            author: [ email: 'david@dmichael.org'],
                            content: 'Loves to roll around on wood floors like a little kid.'
                    ]
            ],
            createdBy: [ email: 'stacey@kibbler.org' ], organization: [ name: 'Kibbler' ]
    )

    pet( meta: [ key: 'name', update: false ],
            name: 'Angelina', species: 'DOG', breed: 'Chihuahua', gender: 'female',
            age: 12, weight: 7, color: 'white / brown', housebroken: true,
            notes: [
                    [
                            author: [ email: 'stacey@kibbler.org' ],
                            content: 'Was successfully treated for mange.  No signs or symptoms remaining.'
                    ],
            ],
            createdBy: [ email: 'stacey@kibbler.org' ], organization: [ name: 'Kibbler' ]
    )

    pet( meta: [ key: 'name', update: false ],
            name: 'Beetlejuice', species: 'DOG', breed: 'Chinese Crested', gender: 'female',
            age: 1, weight: 8, color: 'white hairless', housebroken: false,
            status: 'HOLD',
            notes: [
                    [
                            author: [ email: 'stacey@kibbler.org' ],
                            content: 'Not currently available for adoption.'
                    ],
            ],
            createdBy: [ email: 'stacey@kibbler.org' ], organization: [ name: 'Kibbler' ]
    )

    pet( meta: [ key: 'name', update: false ],
            name: 'Sophie', species: 'CAT', gender: 'female',
            age: 8, weight: 20, color: 'white', housebroken: false, neutered: true,
            notes: [
                    [
                            author: [ email: 'david@dmichael.org' ],
                            content: 'Sophie can be very aggressive against other animals, ' +
                                    'especially around feeding time.  She tends to "bully" the other animals for no ' +
                                    'obvious reason.'
                    ],
            ],
            createdBy: [ email: 'david@dmichael.org' ], organization: [ name: 'Kibbler' ]
    )


    pet( meta: [ key: 'name', update: false ],
            name: 'Caesar', species: 'CAT', gender: 'male',
            age: 8, weight: 16, color: 'black', neutered: true,
            createdBy: [ email: 'david@dmichael.org' ], organization: [ name: 'Kibbler' ]
    )
}