package kibbler

import org.bson.types.ObjectId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User implements UserDetails {

	transient springSecurityService

    ObjectId id
	String email
	String password
    String activationCode

    boolean activated = true
	boolean enabled = true
	boolean accountExpired  = false
	boolean accountLocked   = false
	boolean passwordExpired = false

    Set<String> roles = []

	static constraints = {
        email blank: false, unique: true, index: [unique: true]
        password nullable: true
        activated nullable: true
        activationCode nullable: true
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

	Set<GrantedAuthority> getAuthorities() {
        roles.collect{ role -> [getAuthority: {-> role} ] as GrantedAuthority }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {

        //if the password doesn't appear to be encoded, it was probably changed.
        //TODO obviously a user that creates a 64 character password would have their password exposed and wouldn't
        // be able to log in.  We should make this method smarter.
        if( password && password.size() != 64 ) {
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
