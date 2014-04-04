package kibbler

import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthorityImpl
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

	static constraints = {
        email blank: false, unique: true, email: true
        password nullable: true
        activated nullable: true
        activationCode nullable: true
        invitedBy nullable: true
	}

	static mapping = {
        table 'users'
        email unique: true
        lastLogin formula: "(SELECT MAX(ul.date_created) FROM users_login ul WHERE ul.user_id = id)"
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
    Set<Role> getAuthorities() {
        def authorities
        User.withTransaction{
            authorities = UserRole.findAllByUser(this)*.role as Set
        }
        authorities
    }

    def beforeValidate() {
    }

	def beforeInsert() {
		encodePassword()
        activationCode = generateActivationCode()
	}

	def beforeUpdate() {

        if( isDirty('password') ) {
            encodePassword()
        }
	}

	protected void encodePassword() {
		if( password ) {
            password = springSecurityService.encodePassword(password, username)
        }
	}

    static generateActivationCode() {
        RandomStringUtils.random( 4, true, true ).toLowerCase()
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
