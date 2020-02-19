name := "hiya-soccer-league"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "com.github.scopt" %% "scopt" % "4.0.0-RC2"
libraryDependencies += "org.specs2" %% "specs2-core" % "4.8.3" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.lihaoyi" %% "sourcecode" % "0.1.9"

scalacOptions in Test ++= Seq("-Yrangepos")
