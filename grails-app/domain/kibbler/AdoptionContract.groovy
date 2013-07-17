package kibbler

import org.bson.types.ObjectId

/**
 * Capture all of
 */
class AdoptionContract {
    ObjectId id
    Integer adoptionFeeCents
    String adopterSignature
    String adopterSignatureUrl
    String repSignature
    String pdfS3key
    Date dateCreated

    static belongsTo = [ adoptionRecord: AdoptionRecord ]

    static mapping = {
        version false
        stateless true
    }

    static constraints = {
        adoptionFeeCents nullable: true
        pdfS3key nullable: true
        repSignature nullable: true
        adopterSignatureUrl nullable: true
    }
}
