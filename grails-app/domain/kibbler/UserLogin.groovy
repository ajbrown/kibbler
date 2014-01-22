package kibbler

/**
 * Created by ajbrown on 1/22/14.
 */
class UserLogin {
    User user
    String userAgent
    String ipAddress
    Date createdAt

    static belongsTo = [ user: User ]

    static mapping = {
        version false
        sort createdAt: "desc"
        user index: true
    }

    static constraints = {
        userAgent nullable: true
        ipAddress nullable: true
    }
}
