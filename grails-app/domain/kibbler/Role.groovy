package kibbler

import org.springframework.security.core.GrantedAuthority

class Role implements GrantedAuthority {

	String authority

	static mapping = {
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
