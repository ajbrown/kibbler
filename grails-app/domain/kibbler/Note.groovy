package kibbler

class Note {
    String content

    User author
    Date dateCreated
    Date lastUpdated

    static constraints = {
        author nullable: true
        content blank: false
    }

    static mapping = {
        sort dateCreated: "desc"
    }
}
