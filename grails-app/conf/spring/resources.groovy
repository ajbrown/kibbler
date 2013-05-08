import com.fasterxml.jackson.databind.ObjectMapper
import kibbler.UserService

// Place your Spring DSL code here
beans = {
    objectMapper(ObjectMapper)
    userDetailsService(UserService)
}
