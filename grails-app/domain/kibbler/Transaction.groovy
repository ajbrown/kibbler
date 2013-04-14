package kibbler

import org.bson.types.ObjectId

class Transaction {

    ObjectId id
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
        enteredBy nullable: true, validator: { val, obj ->
            ( !val || val.belongsTo( obj.organization ) ) ?: ['default.organization.mismatch']
        }
        createdBy nullable: true, validator: { val, obj ->
            ( !val || val.belongsTo( obj.organization ) ) ?: ['default.organization.mismatch']
        }
        updatedBy nullable: true
        lastUpdated nullable: true
        pet nullable: true
    }

    static mapping = {
        order dateCreated: "desc"
    }

    def beforeValidate() {
        //EnteredBy should default to the recorder
        if( !enteredBy && createdBy ) {
            enteredBy = createdBy
        }
    }
}
