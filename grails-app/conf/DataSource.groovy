grails {
    mongo {
        host = "localhost"
        port = 27017
        //username = "bigbird"
        //password = "bigbird"
        databaseName = "bigbird"
    }
}
// environment specific settings
environments {
    development {
    }
    test {
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
