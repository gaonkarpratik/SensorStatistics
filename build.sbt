ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "SensorStatisticTask"
  )

resolvers ++= Seq(
  "Maven repo" at "https://mvnrepository.com/artifact/"
)

libraryDependencies += "org.scala-lang"                 % "scala-reflect"                   % "2.13.10"
libraryDependencies += "com.typesafe.akka"             %% "akka-http"                       % "10.2.9"
libraryDependencies += "com.typesafe.akka"             %% "akka-http-spray-json"            % "10.2.9"
libraryDependencies += "org.json4s"                    %% "json4s-jackson"                  % "3.6.7"
libraryDependencies += "ch.qos.logback"                 % "logback-classic"                 % "1.2.10"
libraryDependencies += "org.slf4j"                      % "slf4j-api"                       % "1.7.35"
libraryDependencies += "org.scalatest"                 %% "scalatest"                       % "3.3.0-SNAP2" % Test
