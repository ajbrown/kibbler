package kibbler

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User implements UserDetails {

	transient springSecurityService

    String name
	String email
	String password
    String activationCode

    boolean activated = true
	boolean enabled = true
	boolean accountExpired  = false
	boolean accountLocked   = false
	boolean passwordExpired = false

    User invitedBy
    Date lastLogin

    Date dateCreated
    Date lastUpdated

    static hasMany = [
            roles: Role,
            profiles: Person
    ]

	static constraints = {
        email blank: false, unique: true, email: true, index: [unique: true]
        password nullable: true
        activated nullable: true
        activationCode nullable: true
        invitedBy nullable: true
	}

	static mapping = {
        email unique: true, index: true, indexAttributes: [ unique:true ]
        lastLogin formula: "(SELECT MAX(ul.date_created) FROM user_login ul WHERE ul.user_id = id)"
	}

    /**
     * Determines if a user belongs to the specified organization.
     * @param org Either String representing the id of the organization, or an Organization object.
     * @return
     */
    Boolean belongsTo( org ) {
        def lookup = org instanceof Organization ? org : Organization.load( org )
        OrgRole.countByUserAndOrganization( this, lookup ) > 0
    }

    /**
     * Returns all organizations this user belongs to.
     * @return
     */
    Set<Organization> getOrganizations() {
        OrgRole.findAllByUserAndOrganizationIsNotNull( this )*.organization
    }

    /**
     * Returns all security authorities this user belongs to.  Most users only belong to ROLE_USER.
     * @return
     */
	Set<GrantedAuthority> getAuthorities() {
        roles.collect{ role -> [getAuthority: {-> role} ] as GrantedAuthority }
	}

	def beforeInsert() {
		encodePassword()
        activationCode = generateActivationCode()
	}

	def beforeUpdate() {

        //if the password doesn't appear to be encoded, it was probably changed.
        //TODO obviously a user that creates a 64 character password would have their password exposed and wouldn't
        // be able to log in.  We should make this method smarter.
        if( isDirty('password') ) {
            encodePassword()
        }
	}

	protected void encodePassword() {
		if( password ) {
            password = springSecurityService.encodePassword(password)
        }
	}

    static generateActivationCode() {
        System.currentTimeMillis().encodeAsMD5().substring(4).toLowerCase()
    }

    String getUsername() {
        email
    }

    boolean isAccountNonExpired() {
        !accountExpired
    }

    boolean isAccountNonLocked() {
        !accountLocked
    }

    boolean isCredentialsNonExpired() {
        !passwordExpired
    }

    boolean isEnabled() {
        enabled
    }
}
