package org.kibbler

class Organization {
    String name

    Date dateCreated
    Date lastUpdated

    static hasMany = [
            pets: Pet,
            contacts: Contact,
            members: OrgRole
    ]

    static constraints = {
    }
}
