import Environment._
import Dependencies._
import Version._

// ThisBuild settings take lower precedence,
// but can be shared across the multi projects.
def buildLevelSettings: Seq[Setting[_]] = Seq(
  organization in ThisBuild := "jafaeldon",
  version in ThisBuild      := multiprojectV,
  shellPrompt in ThisBuild  := { state => Project.extract(state).currentRef.project + "> " }
)

def commonSettings: Seq[Setting[_]] = Seq(
  // Scala Version
  scalaVersion := scalaV,
  crossScalaVersions := crossScalaV,

  // Publishing
  resolvers ++= resolutionRepos,
  publishArtifact in packageDoc := false,
  publishArtifact in Test := false,
  publishMavenStyle := true,
  credentials += Credentials("Sonatype Nexus Repository", "oss.sonatype.org", publishUsername, publishPassword),
  publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots") 
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomIncludeRepository := { _ => false },
  licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("http://jafaeldon.com")),
  pomExtra := (
    <scm>
      <url>git@http://github.com/jafaeldon/multiproject.git</url>
      <connection>scm:git:git@http://github.com/jafaeldon/multiproject.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jafaeldon</id>
        <name>James Faeldon</name>
        <url>http://http://github.com/jafaeldon</url>
      </developer>
    </developers>
  ),

  javacOptions in compile ++= Seq(
    "-target", java8V, 
    "-source", java8V, 
    "-Xlint", "-Xlint:-serial"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-Yinline-warnings",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:higherKinds",
    "-language:postfixOps",
    "-language:existentials",
    "-feature"),
  testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-w", "1"),

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
)

lazy val multiprojectRoot: Project = (project in file(".")).
  aggregate(coreProj, sampleProj, benchmarkProj).
  settings(
    buildLevelSettings,
    commonSettings,
    rootSettings,
    publish := {},
    publishLocal := {}
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

lazy val coreProj = (project in file("core")).
  settings(
    commonSettings,
    name := "Core",
    libraryDependencies ++= Seq(
      scalatest   % Test,
      scalacheck  % Test
    )
  )

lazy val sampleProj = (project in file("sample")).
  dependsOn(coreProj).
  settings(
    commonSettings,
    name:= "Sample",
    libraryDependencies ++= Seq(
      scalatest   % Test,
      scalacheck  % Test
    )
  )

lazy val benchmarkProj = (project in utilPath / "benchmark").
  dependsOn(coreProj).
  settings(
    commonSettings,
    name := "Bemnchmark",
    libraryDependencies ++= Seq(
      scalatest   % Test,
      scalacheck  % Test
    )
  )


/* Nested project paths */
def utilPath   = file("util")

