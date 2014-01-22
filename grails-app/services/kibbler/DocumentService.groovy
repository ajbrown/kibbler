package kibbler

class DocumentService {

    def pdfRenderingService

    def loadContractTemplate( Organization org ) {
        ContractTemplate.findByOrganization( org )
    }

    def saveContractTemplate( Organization org, ContractTemplate template ) {
        template.organization = org
        template.save()
    }

    def createAdoptionContract( Organization org ) {
        pdfRenderingService.render()
    }

}
