package kibbler

import org.bson.types.ObjectId

class Organization {

    ObjectId id
    String name

    static hasMany = [
            pets: Pet,
            members: OrgRole,
            people: Person
    ]

    static constraints = {

    }

    def Set<User> getMemberAccounts() {
        members*.user
    }
}
