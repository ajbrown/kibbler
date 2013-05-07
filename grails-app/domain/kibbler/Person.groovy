package kibbler

import org.bson.types.ObjectId

class Person {

    ObjectId id

    String name
    String company
    String address
    String notes

    String phone
    String email

    Boolean adopter = false
    Boolean foster = false
    Boolean available = true

    Boolean doNotAdopt = false

    User linkedAccount

    Date dateCreated
    User createdBy
    Date lastUpdated
    User lastUpdatedBy

    static hasMany = [ fostering: Pet, adopted: Pet ]

    static mappedBy = [ fostering: "foster", adopted: "adopter" ]

    static belongsTo = [
            organization: Organization
    ]

    static mapping = {
        organization index: true

        sort "name"
        linkedAccount cascade: 'none'
    }

    static constraints = {
        company nullable: true
        address nullable: true
        notes nullable: true
        phone nullable: true
        email nullable: true

        doNotAdopt nullable: true, validator: { value, obj ->
            value && obj.linkedAccount ? ['person.doNotAdopt.isTeamMember'] : true }
        adopter nullable : true, validator: { value, obj ->
            value && obj.doNotAdopt ? ['person.adopter.doNotAdopt.listed']: true }
        foster nullable: true, validator: { value, obj ->
            value && obj.doNotAdopt ? ['person.foster.doNotAdopt.listed'] : true }
        available nullable: true, validator: { value, obj ->
            value && obj.doNotAdopt ? ['person.available.listed'] :  true }

        linkedAccount nullable: true

        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

    /**
     * When a person is set to do not adopt, they're also removed as an available foster or adopter.
     * @param doNotAdopt
     */
    void setDoNotAdopt(Boolean doNotAdopt) {
        this.doNotAdopt = doNotAdopt
        if( doNotAdopt ) {
            this.adopter = false
            this.foster = false
            this.available = false
        }
    }
}
