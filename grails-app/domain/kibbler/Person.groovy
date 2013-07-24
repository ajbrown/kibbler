package kibbler

import grails.util.Environment
import org.bson.types.ObjectId

class Person {

    def slugGeneratorService

    ObjectId id
    String slug

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
        id()
        name()
        email email: true, nullable: true, blank: true

        organization()
        linkedAccount nullable: true
        dateCreated()

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


        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

    /**
     * If there's a linked account for this person, and the linked account belongs to this person's organization,
     * they are a team member.
     * @return
     */
    boolean isTeamMember() {
        return this.linkedAccount?.belongsTo( this.organization )
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

    def beforeValidate() {
        //Nasty hack to allow tests to run
        if( Environment.current == Environment.TEST ) {
            slug = name?.toLowerCase().replace( " ", "-" )
        }

        if( !slug ) {
            generateSlug()
        }
    }

    def generateSlug() {
        slug = slugGeneratorService.generateSlug( this.class, "slug", name )
    }
}
