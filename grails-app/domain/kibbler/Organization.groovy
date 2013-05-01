package kibbler

import org.bson.types.ObjectId

class Organization {

    ObjectId id
    String name

    Integer adoptionFeeCents

    static hasMany = [
            pets: Pet,
            members: OrgRole,
            people: Person,
            transactions: Transaction
    ]

    static hasOne = [

    ]

    static constraints = {
        adoptionFeeCents nullable: true
    }

    static mapping = {
        sort "name"
        people sort: "name"
        pets sort: "name"
    }

    def Set<User> getMemberAccounts() {
        members*.user
    }
}
