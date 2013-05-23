package kibbler

import org.bson.types.ObjectId

class Photo {
    ObjectId id
    Pet pet
    String s3key
    String standardUrl
    Boolean primary = false
    Map cloudinaryData

    Date dateCreated
    User uploadedBy

    static belongsTo = Pet

    static constraints = {
        uploadedBy nullable: true
        cloudinaryData nullable: true
    }
}
