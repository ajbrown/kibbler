package kibbler

import grails.converters.JSON

class Pet {

    enum Status { AVAILABLE, HOLD, PLACED, DECEASED }

    static THUMBNAIL_PREFIX = 'http://res.cloudinary.com/hikkwdvwy/image/upload/w_150,h_150,c_thumb,g_faces,r_6'

    static {
        JSON.registerObjectMarshaller( Species, { it.label } )
    }

    String name
    String description
    Species species
    String breed
    String sex
    Integer age
    Integer weight
    String markings

    Placement placement
    List photos
    List documents
    List notes

    //Vitals
    Boolean heartworm
    Boolean housebroken
    Boolean microchipped
    String  microchipId
    String color

    Boolean neutered
    Boolean specialNeeds

    Status status = Status.AVAILABLE

    Date dateCreated
    Date lastUpdated
    User createdBy
    User lastUpdatedBy

    static belongsTo = [ organization: Organization ]
    static hasMany = [
            documents: Document,
            photos: Photo,
            placements: Placement,
            notes: Note,
            labels: String
    ]

    static mapping = {
        organization index: 'pet_organization_id_idx'
        sort "name"
    }

    static constraints = {
        id()
        organization()
        species()

        breed nullable: true
        sex   nullable: true, inList: [ 'male','female' ]

        name blank: false

        age nullable: true
        markings nullable: true
        weight nullable: true
        color nullable: true

        heartworm nullable: true
        housebroken nullable: true
        microchipped nullable: true
        microchipId nullable: true
        neutered nullable: true
        specialNeeds nullable: true

        createdBy nullable: true
        lastUpdatedBy nullable: true

        description nullable: true
        notes nullable: true
        photos nullable: true
    }



    def beforeInsert() {
        //Pets must have an initial placement.
        placement = placement ?: new Placement( type: Placement.Type.RECEIVED, createdBy: createdBy )
    }

    def beforeValidate() {

        //Make sure the status is reflective of placement.
        if( placement?.type in [Placement.Type.ADOPTED, Placement.Type.FOSTERED] ) {
            status = Status.PLACED
        }

    }

    def getThumbnail() {
        def url

        if( !photos ) {
            return
        }

        def first = photos.first()

        if( first && first.cloudinaryData ) {
            url = THUMBNAIL_PREFIX + first.cloudinaryData.public_id + '.' + first.cloudinaryData.format
        }

        url
    }
}
