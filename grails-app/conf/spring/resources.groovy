import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.cloudinary.Cloudinary
import com.fasterxml.jackson.databind.ObjectMapper
import kibbler.UserService

// Place your Spring DSL code here
beans = {
    objectMapper(ObjectMapper)
    userDetailsService(UserService)

    awsCredentials( BasicAWSCredentials, application.config.awsAccessKey, application.config.awsSecretKey )
    amazonS3Client( AmazonS3Client, ref('awsCredentials') )

    cloudinary( Cloudinary, application.config.cloudinary.url )
}
