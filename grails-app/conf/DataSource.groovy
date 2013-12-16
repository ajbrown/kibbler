dataSource {
    pooled = true
    dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
    driverClassName = "org.postgresql.Driver"
    dialect = net.hibernate.dialect.PostgreSQLDialect
    logSql = false

    properties {
        maxActive = 50
        maxIdle = 25
        minIdle = 5
        initialSize = 5
        minEvictableIdleTimeMillis = 60000
        timeBetweenEvictionRunsMillis = 60000
        maxWait = 10000
        validationQuery = "/* ping */"
    }
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            url = "jdbc:postgresql://localhost:5432/kibbler"
            username = "kibbler"
            password = "kibbler"

            // Turn this on if you need to debug, but make sure you turn it back off when you're done
            //logSql = true
        }

        hibernate {
            cache.use_second_level_cache = false
            cache.use_query_cache = false
        }
    }

    test {
        dataSource {
            username = "sa"
            password = ""
            driverClassName = "org.h2.Driver"
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
        }
    }

    production {
        dataSource {
            url = System.getProperty('DB_URL') ?: System.getenv( 'DB_URL' )
            username = System.getProperty('DB_USERNAME') ?: System.getenv( 'DB_USERNAME' )
            password = System.getProperty('DB_PASSWORD') ?: System.getenv( 'DB_PASSWORD' )
            logSql = false
        }
    }
}
