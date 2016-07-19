name := """drool-demo-sample"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.kie" % "kie-ci" % "6.3.0.Final"
//
libraryDependencies += "org.kie" % "kie-api" % "6.3.0.Final"
//
//// https://mvnrepository.com/artifact/org.drools/drools-core
libraryDependencies += "org.drools" % "drools-core" % "6.3.0.Final"

//// https://mvnrepository.com/artifact/org.drools/drools-compiler
libraryDependencies += "org.drools" % "drools-compiler" % "6.3.0.Final"
//



// https://mvnrepository.com/artifact/org.apache.maven/maven-core
libraryDependencies += "org.apache.maven" % "maven-core" % "3.3.9"

// https://mvnrepository.com/artifact/org.apache.maven/maven-artifact
libraryDependencies += "org.apache.maven" % "maven-artifact" % "3.3.9"



resolvers += "Guvnor M2 Repo" at "http://localhost:8080/kie-drools/maven2/"

fork in run := true

routesGenerator := InjectedRoutesGenerator