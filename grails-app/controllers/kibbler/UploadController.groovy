package kibbler

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import org.imgscalr.Scalr
import org.springframework.web.multipart.commons.CommonsMultipartFile

import javax.imageio.ImageIO

class UploadController {
    static int PHOTO_MAX_WIDTH = 600

    static alloweContentTypes = [ 'image/jpeg', 'image/x-png', 'image/png' ]


}
