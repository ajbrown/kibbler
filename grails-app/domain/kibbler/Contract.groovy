package kibbler

/**
 * Capture all of
 */
class Contract {
    Integer feeCents
    String signature
    String signatureUrl
    String repSignature
    String pdfS3key
    Date dateCreated
    Date lastUpdated

    static belongsTo = [ placement: Placement ]

    static mapping = {
        placement index: true
        sort dateCreated: "desc"
    }

    static constraints = {
        feeCents nullable: true
        pdfS3key nullable: true
        repSignature nullable: true
        signatureUrl nullable: true
    }
}
