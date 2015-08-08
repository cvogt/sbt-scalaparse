import sbt._
import sbt.Keys._
import sbt.{ThisBuild, Project}

object SbtScalaParseBuild extends Build {
  val repoKind = SettingKey[String]("repo-kind", "Maven repository kind (\"snapshots\" or \"releases\")")
  val projectName = "sbt-scalaparse"
  val sbtScalariform: Project = Project(
    projectName,
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
      sbtPlugin := true,
      name := projectName,
      description := "sbt plugin for better parse errors using @lihaoyi's ScalaParse",
      organization := "org.cvogt",
      name := "sbt-scalaparse",
      version in ThisBuild := "0.1",
      libraryDependencies ++= Seq(
        "com.lihaoyi" %% "scalaparse" % "0.2.1"
      ),
      scalacOptions ++= List(
        "-unchecked",
        "-deprecation",
        "-feature"
      ),
      resolvers ++= Seq(Resolver.sonatypeRepo("releases"),Resolver.sonatypeRepo("snapshots")),
      organizationName := "Jan Christopher Vogt",
      organization := "org.cvogt",
      repoKind <<= (version)(v => if(v.trim.endsWith("SNAPSHOT")) "snapshots" else "releases"),
      //publishTo <<= (repoKind)(r => Some(Resolver.file("test", file("c:/temp/repo/"+r)))),
      publishTo <<= (repoKind){
        case "snapshots" => Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
        case "releases" =>  Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
      },
      publishMavenStyle := true,
      publishArtifact in Test := false,
      pomIncludeRepository := { _ => false },
      makePomConfiguration ~= { _.copy(configurations = Some(Seq(Compile, Runtime, Optional))) },
      licenses += ("Two-clause BSD-style license", url("http://github.com/cvogt/"+projectName+"/blob/master/LICENSE.txt")),
      homepage := Some(url("http://github.com/cvogt/"+projectName)),
      startYear := Some(2015),
      pomExtra :=
        <developers>
          <developer>
            <id>cvogt</id>
            <name>Jan Christopher Vogt</name>
            <timezone>-5</timezone>
            <url>https://github.com/cvogt/</url>
          </developer>
        </developers>
          <scm>
            <url>git@github.com:cvogt/{projectName}.git</url>
            <connection>scm:git:git@github.com:cvogt/{projectName}.git</connection>
          </scm>
    )
  )
}
