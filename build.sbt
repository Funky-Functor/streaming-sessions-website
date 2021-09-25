name := "streaming-website"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("com.funkyfunctor.streaming.website")

libraryDependencies ++= List(
  Libraries.zhttp,
  Libraries.zioJson,
  Libraries.zioPrelude
)

// Test Configuration
ThisBuild / testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")