import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import grails.converters.JSON
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

    }
    def destroy = {

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
