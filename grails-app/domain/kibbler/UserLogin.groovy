package kibbler

/**
 * Created by ajbrown on 1/22/14.
 */
class UserLogin {
    User user
    String userAgent
    String ipAddress
    Date dateCreated

    static belongsTo = [ user: User ]

    static mapping = {
        table 'users_login'
        version false
        sort dateCreated: "desc"
        user        index: 'idx_user_login_user_created'
        dateCreated index: 'idx_user_login_user_created'
    }

    static constraints = {
        userAgent nullable: true
        ipAddress nullable: true
    }
}
