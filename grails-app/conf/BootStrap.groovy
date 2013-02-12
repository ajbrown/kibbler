import bigbird.Organization
import bigbird.User

class BootStrap {

    def init = { servletContext ->

        def aj     = new User( email: 'aj@synklabs.com', password: '123456').save()
        def david  = new User( email: 'david@synklabs.com', password: '123456' ).save()

    }
    def destroy = {
    }
}
