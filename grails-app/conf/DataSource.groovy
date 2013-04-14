grails {
    mongo {
        host = "localhost"
        port = 27017
        //username = "kibbler"
        //password = "kibbler"
        databaseName = "kibbler"
    }
}
// environment specific settings
environments {
    development {
    }
    test {
    }
    production {
        grails {
            mongo {
                host = "ds047197.mongolab.com"
                port = 47197
                username = "kibblerapp"
                password = "kibbler2013"
                databaseName = "heroku_app11534347"
            }
        }
    }
}
