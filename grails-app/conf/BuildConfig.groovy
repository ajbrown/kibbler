grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.plugin.location.'cacheable-rest' = "../restful-cache-headers"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    //run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    run: false,
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
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
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"

        mavenRepo 'http://repo.spring.io/milestone' //Needed for spring-security-core
        mavenRepo 'https://oss.sonatype.org/content/repositories/snapshots' //Needed for spring-security-rest
    }

    dependencies {
        runtime 'org.postgresql:postgresql:9.3-1100-jdbc4'

        compile 'com.amazonaws:aws-java-sdk:1.4.3'
        compile 'com.cloudinary:cloudinary:1.0.2'
        compile 'com.fasterxml.jackson.core:jackson-databind:2.1.2'
        compile "com.mandrillapp.wrapper.lutung:lutung:0.0.3"
        compile 'org.imgscalr:imgscalr-lib:4.2'
        compile 'org.xhtmlrenderer:core-renderer:R8'

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.52.1"

        // plugins for the compile step
        compile ":ajax-uploader:1.1"
        compile ':cache:1.1.5'
        compile ":cache-headers:1.1.5"
        compile ":mail:1.0.5"
        compile ":marshallers:0.6"
        compile ":rendering:0.4.4"
        compile ":scaffolding:2.0.1"
        compile ":seed-me:0.4.5"
        compile ":spring-security-acl:2.0-RC1"
        compile ":spring-security-core:2.0-RC2"
        compile ":spring-security-rest:1.3.4", { excludes: 'spring-security-core' }

        // plugins needed at runtime but not for compilation
        runtime ":hibernate:3.6.10.14" // or ":hibernate4:4.1.11.6"
        runtime ":database-migration:1.3.8"

        //runtime ":global-json-settings:0.1.1"

        // resources related
        runtime ":resources:1.2.7"
        runtime ":cached-resources:1.1"
        runtime ":less-resources:1.3.3.2"
        //runtime ":yui-minify-resources:0.1.5"
        runtime ":zipped-resources:1.0.1"

        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
    }
}
