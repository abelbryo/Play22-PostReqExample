name := "test-json"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.webjars" %% "webjars-play" % "2.2.2-1",
  "org.webjars" % "bootstrap" % "3.2.0",
  // reCaptcha
  "net.tanesha.recaptcha4j" % "recaptcha4j" % "0.0.7"
)

play.Project.playScalaSettings
