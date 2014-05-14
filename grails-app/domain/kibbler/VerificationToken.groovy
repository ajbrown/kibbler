package kibbler

import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang3.RandomStringUtils

@EqualsAndHashCode
class VerificationToken {
    String token
    String email

    Date dateCreated
    Date lastUpdated

    static marshalling = {
        shouldOutputVersion false
        shouldOutputClass false
    }

    static constraints = {
        email email: true
    }

    static mapping = {
        token index: 'verify_token_idx', sqlType: 'varchar(96)'
    }

    static generate( String email ) {
        new VerificationToken(
            token: UUID.randomUUID().toString(),
            email: email
        )
    }

    String toString() {
        token
    }
}
