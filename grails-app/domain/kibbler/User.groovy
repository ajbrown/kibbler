package kibbler

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

    /**
     * Determines if a user belongs to the specified organization.
     * @param org Either String representing the id of the organization, or an Organization object.
     * @return
     */
    Boolean belongsTo( org ) {
        def lookup = org instanceof Organization ? org : new ObjectId( org )
        OrgRole.countByUserAndOrganization( this, lookup ) > 0
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
