import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.cloudinary.Cloudinary
import com.fasterxml.jackson.databind.ObjectMapper
import com.microtripit.mandrillapp.lutung.MandrillApi
import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer
import kibbler.AuthenticationTokenStorageService
import kibbler.Organization
import kibbler.User
import kibbler.UserService

// Place your Spring DSL code here
beans = {
    objectMapper(ObjectMapper)
    userDetailsService(UserService)

    tokenStorageService( AuthenticationTokenStorageService ) {
        grailsCacheManager = ref( 'grailsCacheManager' )
        userDetailsService = ref( 'userDetailsService' )
    }

    awsCredentials( BasicAWSCredentials, application.config.awsAccessKey, application.config.awsSecretKey )
    amazonS3Client( AmazonS3Client, ref('awsCredentials') )

    cloudinary( Cloudinary, application.config.cloudinary.url )
    mandrillApi( MandrillApi, application.config.mandrill.apikey )

}
