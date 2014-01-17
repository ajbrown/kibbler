package org.kibbler

class User {

	transient springSecurityService

    String name
	String email
	String password
	boolean enabled         = true
	boolean accountExpired  = false
	boolean accountLocked   = false
	boolean passwordExpired = false

	static transients = ['springSecurityService']

	static constraints = {
		email blank: false, unique: true, email: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
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
