/**
 * Library versions
 */
object Versions {
  lazy val scala211 = "2.11.8"
  lazy val scala212 = "2.12.0-M4"

  val java7V      = "7"
  val java8V      = "8"
  val scalaV      = scala211
  val crossScalaV = Seq(scala211, scala212)

  lazy val hadoopV      = Environment.hadoopVersion
  lazy val sparkV       = Environment.sparkVersion
  lazy val cassandraV   = Environment.cassandraVersion
}
