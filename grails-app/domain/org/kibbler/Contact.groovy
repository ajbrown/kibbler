package org.kibbler

class Contact {
    Organization organization
    User referencedUser

    String name
    String address
    String phoneNumber


    static belongsTo = [ Organization ]
    static hasMany   = [ placements: Placement, notes: Note ]

    static constraints = {

        //TODO the referenced user must be part of the organization for this contact.
        referencedUser nullable: true
    }
}
