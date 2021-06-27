name := """autoscout24-report"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % "test"
libraryDependencies += "org.mockito" % "mockito-all" % "1.8.4"
