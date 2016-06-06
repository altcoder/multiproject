credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers ++= Seq(
  Classpaths.sbtPluginReleases,
  Opts.resolver.sonatypeReleases
)


// Github: https://github.com/softprops/bintray-sbt
// Description: An sbt interface for publishing and resolving bintray packages.
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

// Github: https://github.com/softprops/ls
// Website: http://ls.implicit.ly/#publishing
// Description: A scala card catalog http://ls.implicit.ly/. Try and install libraries from ls.implicit.ly
// Usage: > ls -n mylibrary
addSbtPlugin("me.lessis" % "ls-sbt" % "0.1.3")

// Github: https://github.com/spray/sbt-revolver
// Description: An SBT plugin for dangerously fast development turnaround in Scala
// Usage: > ~re-start
addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

// Github: https://github.com/jrudolph/sbt-dependency-graph
// Description: sbt plugin to create a dependency graph for your project
// Usage: > dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")

// Github: https://github.com/sbt/sbt-pgp
// Website: http://www.scala-sbt.org/sbt-pgp/
// Description: The sbt-pgp plugin provides PGP signing for SBT 0.12+
// Usage: > pgp-cmd gen-key
addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

// Github: https://github.com/sbt/sbt-assembly
// Description: Deploy fat JARs. Restart processes. 
// Usage: > assembly
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")

// Github: https://github.com/sbt/sbt-dirty-money
// Description: clean Ivy2 cache
// Usage: > show cleanCacheFiles
addSbtPlugin("com.eed3si9n" % "sbt-dirty-money" % "0.1.0")

// Github: https://github.com/scalastyle/scalastyle-sbt-plugin
// Description: Scalastyle examines your Scala code and indicates potential problems with it.
// Website: http://www.scalastyle.org/sbt.html
// Usage: > scalastyle
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.7.0")

// Github: https://github.com/tpolecat/tut
// Description: doc/tutorial generator for scala
addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.4.2")


