name := "$name$"

organization := "$organization$"

scalaVersion := "$scalaVersion$"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.10"

val circeVersion = "0.12.3"

val circe = Seq(
  "circe-core",
  "circe-parser",
  "circe-generic"

).map("io.circe" %% _ % circeVersion)

val jsoniter = Seq(
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core"   % "2.0.2" % Compile,
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "2.0.2" % Provided // required only in compile-time
)

libraryDependencies ++= circe ++ jsoniter

enablePlugins(JmhPlugin)