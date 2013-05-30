import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import grails.converters.JSON
import grails.util.Environment
import kibbler.AdoptionContractTemplate
import kibbler.EventType
import kibbler.Organization
import kibbler.Person
import kibbler.Pet
import kibbler.Species
import kibbler.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId

class BootStrap {

    ObjectMapper objectMapper

    def init = { servletContext ->

        //Configure object marshalling
        configureObjectMapper()
        configureJSONMarshaller()

        def aj     = new User( email: 'aj@synklabs.com', password: '123456').save()
        def david  = new User( email: 'david@synklabs.com', password: '123456' ).save()

        if( Environment.DEVELOPMENT == Environment.currentEnvironment ) {
            createMockAdoptionContracts()
        }

        //Make sure the two users are created.
        def users = User.count()
        log.info "There are ${users} users in the system."

        JSON.registerObjectMarshaller( EventType ) {
            return it.toString()
        }

        populateMissingSlugs()
    }

    def destroy = {

    }

    def populateMissingSlugs() {

        log.info "Populating missing slugs"

        Organization.findAllBySlugIsNull().each{
            it.generateSlug()
            it.save()
        }

        Pet.findAllBySlugIsNull().each{
            it.generateSlug()
            it.save()
        }

        Person.findAllBySlugIsNull().each {
            it.generateSlug()
            it.save()
        }
    }

    def void createMockAdoptionContracts() {

        def loremIpsum = [

                'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                'mauris non pulvinar ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                'Pellentesque tincidunt, sapien eget\n' +
                'porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus ante.',

                'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                        'mauris non pulvinar' +
                        'ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                        'Pellentesque tincidunt, sapien eget.',

                'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                        'mauris non pulvinar' +
                        'ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                        'Pellentesque tincidunt, sapien eget\n' +
                        'porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus.',

                'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, ' +
                        'mauris non pulvinar' +
                        'ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. ' +
                        'Pellentesque tincidunt, sapien eget\n' +
                        'porttitor rhoncus, elit eros pulvinar mauris.',

        ]


        Organization.list().each {
            if( AdoptionContractTemplate.countByOrganization(it) > 0 ) { return }
            def contract = new AdoptionContractTemplate( organization: it )
            contract.terms = loremIpsum
            contract.save()
        }
    }


    def void configureObjectMapper() {
        objectMapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES )
        objectMapper.enable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS )
    }

    def void configureJSONMarshaller() {
        JSON.registerObjectMarshaller( ObjectId, { it.toString() } )
        JSON.registerObjectMarshaller( Species, { it.label } )
    }
}
