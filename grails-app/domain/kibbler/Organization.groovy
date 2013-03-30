package kibbler

import org.bson.types.ObjectId

class Organization {

    ObjectId id
    String name

    static hasMany = [
            pets: Pet,
            members: OrgRole,
            people: Person,
            transactions: Transaction
    ]

    static constraints = {

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
