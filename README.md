<p align=center><img src="https://alekslitvinenk.github.io/log-shingles/assets/img/logo.jpeg" width="300" height="300"/></p><br>

[![Build Status](https://travis-ci.org/alekslitvinenk/hitcounter.svg?branch=master)](https://travis-ci.org/alekslitvinenk/hitcounter)
[![Coverage Status](https://coveralls.io/repos/github/alekslitvinenk/hitcounter/badge.svg?branch=master)](https://coveralls.io/github/alekslitvinenk/hitcounter?branch=master)
![GitHub](https://img.shields.io/github/license/alekslitvinenk/hitcounter)
# ♦️ Log Shingles

This library allows simple request loging for Akka-Http in any of the following format:
1. Logback
2. SQL(MySQL, MS SQL, Postgress) table
3. Cassandra (soon)
4. MongoDB (soon)
5. Prometheus (soon)
6. Kafka topic (soon)

## 🚀Quick start
1. Add dependency to your sbt project:
   ```scala
   libraryDependencies += "com.alekslitvinenk" %% "logshingles" % "0.1",
   ```
 2. Use log-shingles directives to wrap akka-http routes:
    ```scala
    import com.alekslitvinenk.logshingles.dsl._
    
    val route =
      path("somepath") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>OK</h1>"))
        }
      }
      
    // Log to logback
    val loggedRoute = logbackShingle(route)
    
    // Insert request record into SQL table
    val loggedIntoSQLTableRoute = sqlShingle(route)
    
    // Log to logback and insert record into SQL table
    val loggedToSQLAndLogback = sqlShingle(logbackShingle(route))
    
    Http().bindAndHandle(loggedToSQLAndLogback, host, port)
    ```
