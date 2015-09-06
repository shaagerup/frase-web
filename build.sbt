// Turn this project into a Scala.js project by importing these settings
enablePlugins(ScalaJSPlugin)

name := "Example"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

persistLauncher in Compile := true

persistLauncher in Test := false

testFrameworks += new TestFramework("utest.runner.Framework")

libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.1",
    "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
    "com.github.japgolly.scalajs-react" %%% "core" % "0.9.2"
)

libraryDependencies += "org.scala-js" % "scala-parser-combinators_sjs0.6_2.11" % "1.0.2"

lazy val core = RootProject(file("Frase"))
unmanagedSourceDirectories in Compile += baseDirectory.value / "Frase/src/main"

//val main = Project(id = "application", base = file(".")).dependsOn(core) 