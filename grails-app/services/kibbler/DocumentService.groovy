package kibbler

class DocumentService {

    def pdfRenderingService

    def loadContractTemplate( Organization org ) {
        AdoptionContractTemplate.findByOrganization( org )
    }

    def saveContractTemplate( Organization org, AdoptionContractTemplate template ) {
        template.organization = org
        template.save()
    }

    def createAdoptionContract( Organization org ) {
        pdfRenderingService.render()
    }

}
