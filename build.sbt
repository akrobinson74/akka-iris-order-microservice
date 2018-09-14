import CompilerFlags._

name := "akka-iris-order-prototype"
organization := "com.olx"
version := "1.0"

mainClass := Some("com.olx.iris.Main")

scalaVersion := "2.12.6"

scalacOptions ++= compilerFlags
scalacOptions in (Compile, console) ~= filterExcludedReplOptions

lazy val akkaVersion = "2.5.16"
lazy val akkaHttpVersion = "10.1.5"
lazy val circeVersion = "0.9.3"
lazy val akkaJsonVersion = "1.21.0"

libraryDependencies ++= Seq(
  "akka-actor",
  "akka-persistence",
  "akka-stream"
).map { "com.typesafe.akka" %% _ % akkaVersion } ++ Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.89",
  "com.typesafe.akka" %% "akka-persistence-cassandra-launcher" % "0.89" % Test,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "io.circe" %% "circe-generic" % circeVersion,
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
  "de.heikoseeberger" %% "akka-http-circe" % akkaJsonVersion,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "xyz.driver" %% "spray-json-derivation" % "0.7.0"
)
