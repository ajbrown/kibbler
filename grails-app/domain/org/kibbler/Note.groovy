package org.kibbler

/**
 * Created by ajbrown on 1/13/14.
 */
class Note {
    String content

    User author
    Date dateCreated
    Date lastUpdated

    static constraints = {
        author nullable: true
        content blank: false
    }
}
