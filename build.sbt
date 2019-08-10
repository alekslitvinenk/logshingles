lazy val root = (project in file("."))
  .settings(
    name := "hitcounter",
    version := "0.1",
    scalaVersion := "2.12.8",
    mainClass := Some("com.myapp.Main"),

    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.3.0",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
      "com.typesafe.akka" %% "akka-http" % "10.1.8",
      "com.typesafe.akka" %% "akka-stream" % "2.5.19",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "mysql" % "mysql-connector-java" % "8.0.14",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "org.scalamock" %% "scalamock" % "4.1.0" % Test,
    ),

    scalacOptions ++= Seq("-Ypartial-unification"),

    unmanagedResourceDirectories in Compile += { baseDirectory.value / "src/main/resources" }
  )