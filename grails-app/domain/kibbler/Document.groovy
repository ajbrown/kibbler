package kibbler

import org.bson.types.ObjectId

class Document {

    ObjectId id
    String name
    String s3key
    String contentType
    String fileName

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}
