package bigbird

import org.bson.types.ObjectId

class User {

	transient springSecurityService

    ObjectId id
	String email
	String password
	boolean enabled = true
	boolean accountExpired  = false
	boolean accountLocked   = false
	boolean passwordExpired = false

	static constraints = {
		email blank: false, unique: true
		password blank: false
	}

	static mapping = {
        email unique: true, index: true, indexAttributes: [ unique:true ]
	}

    Set<Organization> getOrganizations() {
        OrgRole.findAllByUser( this )*.organization
    }

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
