package kibbler

import grails.converters.JSON

class Pet {

    static enum Status { AVAILABLE, HOLD, PLACED, DECEASED }
    static enum Species { DOG, CAT }

    static THUMBNAIL_PREFIX = 'http://res.cloudinary.com/hikkwdvwy/image/upload/w_150,h_150,c_thumb,g_faces,r_6'

    static {
        JSON.registerObjectMarshaller( Species, { it.toString().toLowerCase() } )
        JSON.registerObjectMarshaller( Status, { it.toString().toLowerCase() } )
    }
    Organization organization

    String name
    String description
    Species species
    String breed
    String gender
    Integer age
    Integer weight
    String markings

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
        labels index: 'pet_labels_idx'
        notes  index: 'pet_notes_idx'
        documents index: 'pet_documents_idx'
    }

    static constraints = {
        id()
        organization()
        species()

        breed nullable: true
        gender nullable: true, inList: [ 'male','female' ]

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

    def getCurrentPlacement() {
        //TODO we could just use a SortedSet here.
        placements?.sort{ -it.dateCreated }?.first()
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
