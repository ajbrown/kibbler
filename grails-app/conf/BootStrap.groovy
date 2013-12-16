import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import grails.converters.JSON
import grails.util.Environment
import kibbler.*

class BootStrap {

    ObjectMapper objectMapper

    def init = { servletContext ->

        //Configure object marshalling
        configureObjectMapper()
        configureJSONMarshaller()

        setupAdminAccounts()


        if( Environment.DEVELOPMENT == Environment.currentEnvironment ) {
            createMockAdoptionContracts()
        }


        populateMissingSlugs()
    }

    def destroy = {

    }

    def private setupAdminAccounts() {
        //setup roles for aj and david
        def userRole = Role.findOrCreateByAuthority( 'ROLE_USER' ).save()
        def adminRole = Role.findOrCreateByAuthority( 'ROLE_ADMIN' ).save()

        [ 'A.J.':'aj@synklabs.com', 'David' : 'david@synklabs.com' ].each{

            def user = User.findByEmail( it.value )
            if( !user ) {
                user = new User( email: it.value, password: '123456' )
            }
            user.name = it.key
            user.roles = user.roles ?: []
            user.roles << userRole.authority
            user.roles << adminRole.authority
            user.save( failOnError: true )
        }
    }

    def populateMissingSlugs() {

        log.info "Populating missing slugs"

        Organization.findAllBySlugIsNull().each{
            it.generateSlug()
            it.save( failOnError: true )
        }

        Pet.findAllBySlugIsNull().each{
            it.generateSlug()
            it.save( failOnError: true )
        }

        Person.findAllBySlugIsNull().each {
            it.generateSlug()
            it.save( failOnError:  true )
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
        JSON.registerObjectMarshaller( Species, { it.label } )
        JSON.registerObjectMarshaller( EventType ) { it.toString() }
    }
}
