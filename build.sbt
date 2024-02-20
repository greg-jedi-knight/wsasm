val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "wsasm",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
    	"org.rogach" %% "scallop" % "4.1.0",
    	"org.scalameta" %% "munit" % "0.7.29" % Test)
  )
