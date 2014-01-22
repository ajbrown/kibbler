package kibbler

class Organization {

    String name
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
        adoptionFeeCents nullable: true
        description nullable: true
        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

    static mapping = {
        sort "name"
        people sort: "name"
        pets sort: "name"
    }

    def beforeValidate() {

    }

    def Set<User> getMemberAccounts() {
        members*.user
    }

    def String toString() {
        "${this.name ?:  '(Unnamed)'}"
    }
}
