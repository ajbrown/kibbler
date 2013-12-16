package kibbler

class AdoptionApplication {
    String name
    String email
    String phone

    Pet pet

    Date dateCreated

    static belongsTo = Pet

    static constraints = {
    }
}
