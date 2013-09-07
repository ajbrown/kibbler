package kibbler

import org.bson.types.ObjectId

class AdoptionApplication {
    ObjectId id
    String name
    String email
    String phone

    Pet pet

    Date dateCreated

    static belongsTo = Pet

    static constraints = {
    }
}
