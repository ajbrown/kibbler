package kibbler

class AdoptionApplication {
    String name
    String email
    String phone

    Pet pet

    Date dateCreated

    static belongsTo = Pet

    static mapping = {
        pet index: 'idx_adoptapp_pet_created'
        dateCreated index: 'idx_adoptapp_pet_created'
        sort dateCreated: 'desc'
    }

    static constraints = {
    }
}
