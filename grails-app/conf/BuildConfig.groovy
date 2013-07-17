grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.plugin.location.'slug-generator' = 'plugins/grails-slug-generator'

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.desirableobjects.co.uk/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        // runtime 'mysql:mysql-connector-java:5.1.20'

        compile 'org.imgscalr:imgscalr-lib:4.2'
        compile 'com.amazonaws:aws-java-sdk:1.4.3'
        compile 'com.cloudinary:cloudinary:1.0.2'

        compile 'org.xhtmlrenderer:core-renderer:R8'
        compile 'com.fasterxml.jackson.core:jackson-databind:2.1.2'
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        runtime ":jquery:1.8.3"
        runtime ":resources:1.1.6"
        runtime ":zipped-resources:1.0"
        runtime ":cached-resources:1.0"
        runtime ":yui-minify-resources:0.1.4"
        runtime ":twitter-bootstrap:2.2.2"


        compile ':heroku:1.0.1'
        compile ':cloud-support:1.0.8'

        compile ":font-awesome-resources:3.0"
        compile ":jquery-ui:1.8.24"
        compile ":mongodb:1.3.0"
        compile ':cache:1.0.1'
        compile ":fields:1.3"
        compile ":rendering:0.4.3"

        compile ":ajax-uploader:1.1"

        compile ":cache-headers:1.1.5"

        test(":spock:0.7") {
            exclude "spock-grails-support"
        }

        build ":tomcat:$grailsVersion"

    }
}
