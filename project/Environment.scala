import scala.util.Properties

/**
 * Load environment variables into SBT (could be versions or any other values)
 */
object Environment {
  def either(environmentVariable: String, default: String): String =
    Properties.envOrElse(environmentVariable, default)

  lazy val hadoopVersion    = either("SPARK_HADOOP_VERSION", "2.6.1")
  lazy val sparkVersion     = either("SPARK_VERSION", "1.5.1")
  lazy val cassandraVersion = either("CASSANDRA_VERSION", "2.1.5")
  lazy val publishUsername  = either("PUBLISH_USERNAME", "anonymous")
  lazy val publishPassword  = either("PUBLISH_PASSWORD", "password")
}
