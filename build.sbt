
lazy val root = (project in file("."))
  .settings(
    organization := "com.alekslitvinenk",
    name := "logshingles",
    version := "0.1",
    scalaVersion := "2.12.19",

    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.1.11",
      "com.typesafe.akka" %% "akka-stream" % "2.6.6",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
      "com.typesafe.akka" %% "akka-slf4j" % "2.6.6",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.slick" %% "slick" % "3.3.2",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
      "mysql" % "mysql-connector-java" % "8.0.18",
      "org.scalatest" %% "scalatest" % "3.1.1" % Test,
      "org.scalamock" %% "scalamock" % "4.4.0" % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.6" % Test,
      "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11"  % Test,
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