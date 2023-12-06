ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "caliban-client-example",
    libraryDependencies ++= Seq(
      "com.github.ghostdogpr" %% "caliban" % "2.4.3",
      "com.github.ghostdogpr" %% "caliban-client" % "2.4.3",
      "com.softwaremill.sttp.client3" %% "http4s-backend" % "3.9.1",
      "org.http4s" %% "http4s-ember-client" % "0.23.23"
    )
  )
  .enablePlugins(CalibanPlugin)

