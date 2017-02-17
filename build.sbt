name := "CS474P2"
version := "0.1"
organization := "org.myorganization"

// project description
description := "Papers Parsing Project"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false
javaOptions += "-XX:MaxPermSize=4096"
// library dependencies. (orginization name) % (project name) % (version)
libraryDependencies ++= Seq(
  "org.jgrapht" % "jgrapht-core" % "1.0.0",
  "com.scitools" % "understand.plugin" % "1.1.3"
)
