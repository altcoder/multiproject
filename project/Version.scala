/**
 * Library versions
 */
object Version {
  lazy val scala210 = "2.10.5"
  lazy val scala211 = "2.11.7"

  val multiprojectV  = "0.1.0-SNAPSHOT"

  val java7V      = "7"
  val java8V      = "8"
  val scalaV      = scala211
  val crossScalaV = Seq(scala210, scala211)

  lazy val hadoopV      = Environment.hadoopVersion
  lazy val sparkV       = Environment.sparkVersion
  lazy val cassandraV   = Environment.cassandraVersion
}
