package org.kibbler

import grails.converters.JSON

class Pet {

    static {
        JSON.registerObjectMarshaller( Pet.Species ) { Pet.Species species ->
            species.toString().toLowerCase()
        }
    }

    enum Species { CAT, DOG }

    String name
    String description
    Species species
    String sex
    Integer age
    Integer weight
    String markings

    Placement placement

    static hasMany = [ notes: Note, labels: String, placements: Placement ]

    static constraints = {
        sex inList: ['male','female']
        description nullable: true
        age nullable: true
        weight nullable: true
        markings nullable: true
    }

    def beforeInsert() {
        placement = new Placement( type: Placement.Type.RECEIVED )
    }

}
