package kibbler

import grails.converters.JSON

class Placement {
    enum Type { ADOPTED, FOSTERED, RECEIVED }

    Pet pet
    Type type
    Person with
    Contract contract
    Organization organization

    User createdBy
    Date dateCreated
    Date lastUpdated

    static {
        JSON.registerObjectMarshaller( Placement.Type ) {
            it.toString().toLowerCase()
        }
    }

    static belongsTo = [ pet: Pet, organization: Organization ]
    static hasOne = [ contract: Contract ]

    static mapping = {
        sort dateCreated: "desc"
    }

    static constraints = {
        with nullable: true
        createdBy nullable: true
    }
}
