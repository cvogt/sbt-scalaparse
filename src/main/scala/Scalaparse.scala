package org.cvogt

import sbt._
import sbt.Keys._

import fastparse.core.Result
import scalaparse.Scala

private object Scalaparse {
  def apply(
    sourceDirectories: Seq[File],
    includeFilter:     FileFilter,
    excludeFilter:     FileFilter,
    configuration:     Configuration,
    streams:           TaskStreams
  ) = {

    def log(label: String, logger: Logger)(message: String)(count: String) =
      logger.info(message.format(count, label))

    val files = sourceDirectories.descendantsExcept(includeFilter, excludeFilter).get.toSet
    files.filter(_.exists).foreach{ file =>
      val code = IO.read(file)
      Scala.CompilationUnit.parse(code) match {
        case f: Result.Failure => streams.log.error( generateErrorMessage( file.toString, code, f.index ) )
        case _ =>
      }
    }
  }

  def generateErrorMessage(file: String, code: String, pos: Int) = {
    val lineBreaks = Stream.iterate(
      code.indexOf("\n")
    )(
      i => code.indexOf("\n",i+1)
    ).take(100000).takeWhile(_ != -1).toVector
    
    val lineBreaksBefore = lineBreaks.takeWhile(_ < pos)
    val lineBreakBefore = lineBreaksBefore.last
    val line = code.drop( lineBreaksBefore.last + 1 ).takeWhile( _ != '\n' )

    println(s""" '$line' """)

    (
      file ++ ":" ++ (lineBreaksBefore.size + 1).toString ++ ": ScalaParse error" ++ "\n"
      ++
      line ++ "\n"
      ++
      (" " * (pos - lineBreakBefore - 1)) ++ "^"
    )    
  }
}
