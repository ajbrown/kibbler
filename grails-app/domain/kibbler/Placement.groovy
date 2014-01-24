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
        organization index: 'placement_organization_id_idx'
        pet index: 'idx_placement_pet_created'
        with index: 'idx_placement_person_created'
        sort dateCreated: "desc"
        dateCreated index: 'idx_placement_pet_created,idx_placement_person_created'
    }

    static constraints = {
        with nullable: true
        createdBy nullable: true
    }
}
