package bigbird

import org.bson.types.ObjectId

class Organization {

    ObjectId id
    String name


    static hasMany = [
            pets: Pet,
            members: OrgRole,
            contacts: Person
    ]

    static constraints = {

    }

    def Set<User> getMemberAccounts() {
        members*.user
    }
}
