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
grails.project.dependency.resolver = "maven"
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
        mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.desirableobjects.co.uk/"
    }

    dependencies {
        compile 'org.imgscalr:imgscalr-lib:4.2'
        compile 'com.amazonaws:aws-java-sdk:1.4.3'
        compile 'com.cloudinary:cloudinary:1.0.2'
        compile 'org.xhtmlrenderer:core-renderer:R8'
        compile 'com.fasterxml.jackson.core:jackson-databind:2.1.2'

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        runtime ":cached-resources:1.0"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.2.RC2"
        runtime ":twitter-bootstrap:2.3.2"
        runtime ":yui-minify-resources:0.1.5"
        runtime ":zipped-resources:1.0"

        build ":improx:0.3"
        build ":tomcat:$grailsVersion"

        compile ":ajax-uploader:1.1"
        compile ":cache-headers:1.1.5"
        compile ":fields:1.3"
        compile ":font-awesome-resources:3.2.1.1"
        compile ":jquery-ui:1.8.24"
        compile ":mongodb:1.3.0"
        compile ":rendering:0.4.4"
        compile ':cache:1.1.1'
        compile ':cloud-support:1.0.11'
        compile ':heroku:1.0.1'
        compile ":spring-security-core:1.2.7.3"

        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
    }
}
