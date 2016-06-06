import sbt._
import Version._

object Dependencies {
  val resolutionRepos = Seq(
    "Local Maven Repository"    at Path.userHome.asFile.toURI.toURL + ".m2/repository",
    "Maven2 Dev Repository"     at "http://download.java.net/maven/2",
    "Typesafe Repository"       at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype Snapshots"        at "http://oss.sonatype.org/content/repositories/snapshots",
    "Scalaz Bintray Repository" at "https://dl.bintray.com/scalaz/releases",
    "Akka Snapshot Repository"  at "http://repo.akka.io/snapshots/",
    "NL4J Repository"           at "http://nativelibs4java.sourceforge.net/maven/"
  )

  val typesafeConfig    = "com.typesafe"        % "config"        % "1.3.0"
  val typesafeLogging   = "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  val scalatest     = "org.scalatest"      %% "scalatest"   % "2.2.4" 
  val scalacheck    = "org.scalacheck"      %% "scalacheck"  % "1.12.3"

  val sparkCore         = "org.apache.spark"    %% "spark-core"   % sparkV  % "provided"
  val hadoop            = "org.apache.hadoop"   % "hadoop-client" % hadoopV % "provided"
}

