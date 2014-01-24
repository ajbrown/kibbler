package kibbler

class Person {

    String name
    String email
    String company
    String address
    String phone

    Boolean adopter = false
    Boolean foster = false
    Boolean available = true
    Boolean doNotAdopt = false

    User linkedAccount
    Boolean teamMember

    Date dateCreated
    User createdBy
    Date lastUpdated
    User lastUpdatedBy

    static hasMany = [
            placements: Placement,
            notes: Note,
            labels: String
    ]

    static belongsTo = [ organization: Organization ]

    static mapping = {
        organization index: 'person_organization_id_idx'
        teamMember formula: "(select count(id) from org_role r where r.organization_id = organization_id and r.user_id = linked_account_id) > 0"
        sort "name"
        linkedAccount cascade: 'none', index: true
        labels index: 'person_labels_idx'
        notes index: 'person_notes_idx'
    }

    static constraints = {
        id()
        name()
        email email: true, nullable: true, blank: true

        organization()
        linkedAccount nullable: true
        dateCreated()

        company nullable: true
        address nullable: true
        notes nullable: true
        phone nullable: true
        email nullable: true

        doNotAdopt nullable: true, validator: { value, obj ->
            value && obj.linkedAccount ? ['person.doNotAdopt.isTeamMember'] : true }
        adopter nullable : true, validator: { value, obj ->
            value && obj.doNotAdopt ? ['person.adopter.doNotAdopt.listed']: true }
        foster nullable: true, validator: { value, obj ->
            value && obj.doNotAdopt ? ['person.foster.doNotAdopt.listed'] : true }
        available nullable: true, validator: { value, obj ->
            value && obj.doNotAdopt ? ['person.available.listed'] :  true }


        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

    /**
     * When a person is set to do not adopt, they're also removed as an available foster or adopter.
     * @param doNotAdopt
     */
    void setDoNotAdopt(Boolean doNotAdopt) {
        this.doNotAdopt = doNotAdopt
        if( doNotAdopt ) {
            this.adopter = false
            this.foster = false
            this.available = false
        }
    }

    def beforeValidate() {
    }
}
