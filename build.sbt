name := "sponge-play"

organization := "org.spongepowered"

version := "1.0.0-SNAPSHOT"

lazy val `spongeplay` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(ws, specs2 % Test)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "javax.mail" % "mail" % "1.4.7"
)

crossPaths := false

credentials += Credentials(
  sys.props.getOrElse("repo.name", "Sponge Repo"),
  sys.props.get("repo.url").map(new URL(_).getHost).getOrElse(""),
  sys.props.getOrElse("repo.user", ""),
  sys.props.getOrElse("repo.pwd", "")
)

publishTo <<= version { (v: String) =>
  val repoName = sys.props.get("repo.name")
  val repoUrl = sys.props.get("repo.url")
  if (repoName.isDefined && repoUrl.isDefined)
    Some(repoName.get at repoUrl.get)
  else
    Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.ivy2/local")))
}

// Replace default publish task with the one from sbt-aether-deploy
overridePublishSettings
