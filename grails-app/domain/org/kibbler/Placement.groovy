package org.kibbler

import grails.converters.JSON

class Placement {
    enum Type { ADOPTED, FOSTERED, RECEIVED }

    Pet pet
    Type type
    Contact with

    Date dateCreated
    Date lastUpdated

    static {
        JSON.registerObjectMarshaller( Placement.Type ) {
            it.toString().toLowerCase()
        }
    }

    static belongsTo = [ pet: Pet ]

    static mapping = {
        sort dateCreated: "desc"
    }

    static constraints = {
        type inList: [ Type.FOSTERED, Type.ADOPTED ]
        with nullable: true
    }
}
