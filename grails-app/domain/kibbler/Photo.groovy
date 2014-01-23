package kibbler

class Photo {
    Pet pet
    String s3key
    String standardUrl
    Boolean primary = false
    Map cloudinaryData

    Date dateCreated
    User uploadedBy

    static belongsTo = Pet

    static mapping = {
        primary column: 'is_primary'
        pet index: true
    }

    static constraints = {
        uploadedBy nullable: true
        cloudinaryData nullable: true
        primary nullable: true
    }

    def String getThumbnailUrl() {
        if( !cloudinaryData ) {
            return null
        }

        'http://res.cloudinary.com/hikkwdvwy/image/upload/w_50,h_50,c_thumb,g_faces,r_6/' +
                cloudinaryData.public_id + '.' + cloudinaryData.format
    }

    def String getMediumUrl() {
        if( !cloudinaryData ) {
            return null
        }

        'http://res.cloudinary.com/hikkwdvwy/image/upload/w_300,h_300,c_thumb,g_faces,r_10/' +
                cloudinaryData.public_id + '.' + cloudinaryData.format
    }

    def String getLargeUrl() {
        if( !cloudinaryData ) {
            return null
        }

        'http://res.cloudinary.com/hikkwdvwy/image/upload/w_500,h_500,c_thumb,g_faces,r_10/' +
                cloudinaryData.public_id + '.' + cloudinaryData.format
    }
}
