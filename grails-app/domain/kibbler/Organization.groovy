package kibbler

import org.bson.types.ObjectId

class Organization {

    def slugGeneratorService

    ObjectId id
    String name
    String slug
    String description

    Integer adoptionFeeCents

    Date dateCreated
    User createdBy
    Date lastUpdated
    User lastUpdatedBy

    static hasMany = [
            pets: Pet,
            members: OrgRole,
            people: Person,
            transactions: Transaction
    ]

    static constraints = {
        id()
        name()
        slug()

        adoptionFeeCents nullable: true

        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

    static mapping = {
        sort "name"
        slug index: true
        people sort: "name"
        pets sort: "name"
    }

    def beforeInsert() {
        generateSlug()
    }

    def Set<User> getMemberAccounts() {
        members*.user
    }

    /**
     * Generate the slug for this
     */
     def void generateSlug() {
        slug = slugGeneratorService.generateSlug( this.class, "slug", name, true )
    }

    def String toString() {
        "${this.name ?:  '(Unnamed)'}"
    }
}
