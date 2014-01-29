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
    }

    def destroy = {

    }

    def void configureObjectMapper() {
        objectMapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES )
        objectMapper.enable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS )
    }
}
