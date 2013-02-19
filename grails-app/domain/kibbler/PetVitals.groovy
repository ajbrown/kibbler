package kibbler

class PetVitals {

    Boolean heartworm
    Boolean housebroken
    Boolean microchipped
    String  microchipId
    Boolean neutered
    Boolean specialNeeds

    static constraints = {
        heartworm nullable: true
        housebroken nullable: true
        microchipped nullable: true
        microchipId nullable: true
        neutered nullable: true
        specialNeeds nullable: true
    }

    static mapping = {
        version: false
    }
}
