ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.0"

lazy val root = (project in file("."))
  .settings(
    name := "Exercises441"
  )
// https://mvnrepository.com/artifact/com.knuddels/jtokkit
libraryDependencies += "com.knuddels" % "jtokkit" % "1.1.0"
// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.4.0"
// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-mapreduce-client-core
libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.4.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-jobclient" % "3.4.0"
libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "2.4.0" // Use the latest version
libraryDependencies += "com.knuddels" % "jtokkit" % "1.1.0"
libraryDependencies ++= Seq(
  "org.scalanlp" %% "breeze" % "2.1.0",
  "org.scalanlp" %% "breeze-natives" % "2.1.0"
)
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "1.0.0-M2"
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "1.0.0-M2"
libraryDependencies += "org.deeplearning4j" % "deeplearning4j-nlp"  % "1.0.0-M2"
libraryDependencies +=  "org.tensorflow"         %  "tensorflow"                % "1.15.0"
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.6" // or the latest version
//libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.13" // Match the version to your slf4j-api
//libraryDependencies += Seq(
//  "org.deeplearning4j" % "deeplearning4j-core" % "1.0.0-M2",
////  "org.deeplearning4j" % "deeplearning4j-nlp"  % "1.0.0-M2",
////  "org.nd4j"            % "nd4j-native-platform" % "1.0.0-M2",
////  "org.slf4j"           % "slf4j-api"            % "2.0.12",
////  "ch.qos.logback"      % "logback-classic"      % "1.5.6" // Logging implementation
//)
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.19" % "test"
libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.30", // or any version you prefer
  "ch.qos.logback" % "logback-classic" % "1.2.3" // or any version you prefer
)
