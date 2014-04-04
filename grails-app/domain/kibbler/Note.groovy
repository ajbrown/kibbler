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
        content length: 4000, sqlType: 'text'
        author index: 'note_author_idx'
        sort dateCreated: "desc"
    }
}
