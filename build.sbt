ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

// Parent Directory
lazy val root = (project in file("."))
  .enablePlugins(ScalastylePlugin)
  .settings(
    name := "SBT-Assignment-Krishna",

    libraryDependencies ++= Seq(

        // JDBC Dependencies
        "mysql" % "mysql-connector-java" % "8.0.32",
        "org.postgresql" % "postgresql" % "42.5.4",

        // Joda Date/Time Dependencies
        "joda-time" % "joda-time" % "2.12.2",
        "org.joda" % "joda-convert" % "2.2.2"
    ),

    // Scala style plugin which give warning of all modules.
    scalastyleConfig := baseDirectory.value / "scalastyle-config.xml",
    scalastyleTarget := target.value / "scalastyle-report.xml",
    scalastyleFailOnError := true,
  )

// Utils Module
lazy val utils = (project in file("utils"))
  .enablePlugins(ScalastylePlugin)
  .settings(
      name := "utils",
  )


// Core Module
lazy val core = (project in file("core"))
  .enablePlugins(ScalastylePlugin)
  .settings(
      name := "core",

      // Scala test dependency in core module with test scope
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test,

      // Classpath of utils module into core module
      unmanagedClasspath in Compile += (baseDirectory in ThisBuild).value.getParentFile / "utils" / "target" / "scala-2.13" / "classes",


  )
  .dependsOn(utils)
  .aggregate(utils)

// Cached Resolution feature
updateOptions := updateOptions.value.withCachedResolution(true)

// Project (built in #1) in core module as a dependency
libraryDependencies += "com.knoldus" % "SBT-Assignment-Krishna" % "0.1"

