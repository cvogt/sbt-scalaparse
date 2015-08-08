package org.cvogt

import sbt._
import sbt.Keys._

object SbtScalaparse extends AutoPlugin {
  override def trigger = allRequirements // always auto-enable
  
  override lazy val projectSettings = Seq(
    includeFilter in parse := "*.scala",
    compileInputs in (Compile, compile) <<= (compileInputs in (Compile, compile)) dependsOn (parse in Compile),
    compileInputs in (Test, compile) <<= (compileInputs in (Test, compile)) dependsOn (parse in Test)
  ) ++ inConfig(Compile)(configSettings) ++ inConfig(Test)(configSettings)
  
  val parse: TaskKey[Unit] =
    TaskKey[Unit](
      "parse",
      "Parse Scala sources using Scalaparse"
    )

  def configSettings: Seq[Setting[_]] =
    List(
      (sourceDirectories in parse) := List(scalaSource.value),
      parse := Scalaparse(
        (sourceDirectories in parse).value.toList,
        (includeFilter in parse).value,
        (excludeFilter in parse).value,
        configuration.value,
        streams.value
      )
    )
}
