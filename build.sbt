import com.typesafe.sbt.pgp.PgpKeys.publishSigned
import sbtrelease.Version
import bintray._
import Environment._
import Dependencies._
import Versions._

val ReleaseTag = """^release/([\d\.]+a?)$""".r

lazy val contributors = Seq(
  "jafaeldon" -> "James Faeldon"
)

// ThisBuild settings take lower precedence,
// but can be shared across the multi projects.
def buildLevelSettings: Seq[Setting[_]] = Seq(
  organization in ThisBuild := "jafaeldon",
  shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
)

def commonSettings: Seq[Setting[_]] = Seq(
  // Scala Version
  scalaVersion := scalaV,
  crossScalaVersions := crossScalaV,

  resolvers ++= resolutionRepos,
  resolvers += Resolver.jcenterRepo,

  javacOptions in compile ++= Seq(
    "-target", java8V, 
    "-source", java8V, 
    "-Xlint", "-Xlint:-serial"),
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Yinline-warnings",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:higherKinds",
    "-language:postfixOps",
    "-language:existentials",
    "-Xfatal-warnings",
    "-Yno-adapted-args",
    "-Ywarn-value-discard",
    "-Ywarn-unused-import"
  ),
  scalacOptions in (Compile, console) ~= {_.filterNot("-Ywarn-unused-import" == _)},
  scalacOptions in (Test, console) <<= (scalacOptions in (Compile, console)),

  // Assembly settings
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "META-INF/MANIFEST.MF"                        => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)  
  }
) ++ testSettings ++ scalaDocSettings ++ publishSettings ++ releaseSettings

lazy val testSettings = Seq(
  parallelExecution in Test := false,
  testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-w", "1"),
  testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oDF"),
  publishArtifact in Test := false
)

lazy val scalaDocSettings = {
  def scmBranch(v: String) = {
    val Some(ver) = Version(v)
    if(ver.qualifier.exists(_ == "-SNAPSHOT"))
      // support branch (0.1.0-SNAPSHOT -> series/0.1)
      s"series/${ver.copy(qualifier = None).string}"
    else
      // release tag (0.1.0-M1 -> v0.1.0-M1)
      s"v${ver.string}"
  }
  Seq(
    scalacOptions in (Compile, doc) ++= Seq(
      "-doc-source-url", s"${scmInfo.value.get.browseUrl}/tree/${scmBranch(version.value)}€{FILE_PATH}.scala",
      "-sourcepath", baseDirectory.in(LocalRootProject).value.getAbsolutePath,
      "-implicits",
      "-implicits-show-all"
    ),
    scalacOptions in (Compile, doc) ~= { _ filterNot { _ == "-Xfatal-warnings" } },
    autoAPIMappings := true
  )
}

lazy val publishSettings = Seq(
  publishArtifact in packageDoc := false,
  publishMavenStyle := true,
  pomIncludeRepository := { _ => false },
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("http://jafaeldon.com")),
//  publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))),
/* // For Sonatype Release
  credentials += Credentials("Sonatype Nexus Repository", "oss.sonatype.org", publishUsername, publishPassword),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots") 
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
 */
  pomExtra := {
    <scm>
      <url>git@http://github.com/jafaeldon/multiproject.git</url>
      <connection>scm:git:git@http://github.com/jafaeldon/multiproject.git</connection>
    </scm>
    <developers>
    {for ((username, name) <- contributors) yield
      <developer>
        <id>{username}</id>
        <name>{name}</name>
        <url>http://github.com/{username}</url>
      </developer>
    }
    </developers>
  },
  pomPostProcess := { node =>
    import scala.xml._
    import scala.xml.transform._
    def stripIf(f: Node => Boolean) = new RewriteRule {
      override def transform(n: Node) =
        if (f(n)) NodeSeq.Empty else n
    }
    val stripTestScope = stripIf { n => n.label == "dependency" && (n \ "scope").text == "test" }
    new RuleTransformer(stripTestScope).transform(node)(0)
  }
)

lazy val noPublish = Seq(
  publish := (),
  publishLocal := (),
  publishSigned := (),
  publishArtifact := false
)

lazy val releaseSettings = Seq(
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value
)

lazy val multiproject: Project = (project in file(".")).
  aggregate(core, sample, benchmark).
  settings(
    buildLevelSettings,
    commonSettings,
    rootSettings,
    noPublish
  )

def rootSettings = Seq(
  initialCommands in console:=
  """
    import jafaeldon.core._
    import jafaeldon.sample._
    import jafaeldon.benchmark._
  """
)

/* ** subproject declarations ** */

lazy val core = (project in file("core")).
  settings(
    commonSettings,
    name := "core",
    libraryDependencies ++= Seq(
      scalatest   % Test,
      scalacheck  % Test
    )
  )

lazy val sample = (project in file("sample")).
  dependsOn(core).
  settings(
    commonSettings,
    name:= "sample",
    libraryDependencies ++= Seq(
      scalatest   % Test,
      scalacheck  % Test
    )
  )

lazy val benchmark = (project in utilPath / "benchmark").
  dependsOn(core).
  settings(
    commonSettings,
    noPublish,
    name := "benchmark",
    libraryDependencies ++= Seq(
      scalatest   % Test,
      scalacheck  % Test
    )
  )

lazy val docs = project.in(file("docs")).
  dependsOn(core).
  settings(
    commonSettings,
    tutSettings,
    name := "docs",
    tutSourceDirectory := file("docs") / "src",
    tutTargetDirectory := file("docs"),
    scalacOptions ~= {_.filterNot("-Ywarn-unused-import" == _)}
  )

/* Nested project paths */
def utilPath   = file("util")

