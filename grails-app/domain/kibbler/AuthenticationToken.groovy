package kibbler

import org.apache.commons.lang.builder.HashCodeBuilder

class AuthenticationToken implements Serializable {

    private static final long serialVersionUID = 20140401

    String tokenValue
    String username

    Date dateCreated

    static mapping = {
        tokenValue unique: true, index: 'token_idx'
        username length: 64
        version false
        cache true
    }

    static constraints = {
    }

    def beforeInsert() {
        tokenValue = tokenValue.encodeAsSHA256()
    }

    boolean equals(other) {
        if (!( other instanceof AuthenticationToken )) {
            return false
        }

        other.tokenValue == tokenValue && other.username == username
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if ( tokenValue ) builder.append( tokenValue )
        if ( username ) builder.append( username )
        builder.toHashCode()
    }
}
