package kibbler

class Transaction {

    Organization organization
    String category
    Integer amountCents
    Pet pet
    User enteredBy

    User createdBy
    Date dateCreated

    User updatedBy
    User lastUpdated

    static belongsTo = Organization

    static constraints = {
        enteredBy nullable: true, validator: { val, obj, errors ->
            ( !val || val.belongsTo( obj.organization ) ) ?: ['organization.mismatch']
        }
        createdBy nullable: true, validator: { val, obj ->
            ( !val || val.belongsTo( obj.organization ) ) ?: ['organization.mismatch']
        }
        updatedBy nullable: true
        lastUpdated nullable: true
        pet nullable: true
    }

    static mapping = {
        organization index: 'idx_transaction_organization_date_created'
        dateCreated index: 'idx_transaction_organization_date_created'

        sort "dateCreated"
        order "desc"
    }

    def beforeValidate() {
        //EnteredBy should default to the recorder
        if( !enteredBy && createdBy ) {
            enteredBy = createdBy
        }
    }
}
