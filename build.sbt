
name := "aws-pinpoint-voice-techhub"

version := "0.1"

scalaVersion := "2.13.7"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.4"
val awsSdk = "2.17.61"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "software.amazon.awssdk" % "aws-sdk-java" % awsSdk

)
