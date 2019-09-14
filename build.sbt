
lazy val root = (project in file("."))
  .settings(
    organization := "com.alekslitvinenk",
    name := "logshingles",
    version := "0.1",
    scalaVersion := "2.12.8",

    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.1.8",
      "com.typesafe.akka" %% "akka-stream" % "2.5.24",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
      "com.typesafe.akka" %% "akka-slf4j" % "2.5.24",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.slick" %% "slick" % "3.3.2",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
      "mysql" % "mysql-connector-java" % "8.0.17",
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "org.scalamock" %% "scalamock" % "4.4.0" % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.24" % Test,
      "com.typesafe.akka" %% "akka-http-testkit" % "10.1.9"  % Test,
    ),

    scalacOptions ++= Seq("-Ypartial-unification"),

    unmanagedResourceDirectories in Compile += { baseDirectory.value / "src/main/resources" }
  )

addCommandAlias(
  "build",
  """|;
     |clean;
     |assembly;
  """.stripMargin)