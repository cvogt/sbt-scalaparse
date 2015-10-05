sbt plugin to run @lihaoyi's ScalaParse

Why? Much better and faster parse errors than Scalac.

Install
addSbtPlugin("org.cvogt" % "sbt-scalaparse" % "0.2")

Usage
sbt> parse
[error] /Users/chris/someproject/SomeScalaFile.scala:441: ScalaParse error
[error]     val foo =
[error]     val bar = 5
[error]     ^
[success] Total time: 1 s, completed 08.08.2015 19:40:12

sbt> compile
[error] /Users/chris/someproject/SomeScalaFile.scala:441: ScalaParse error
[error]     val foo =
[error]     val bar = 5
[error]     ^
[success] Total time: 1 s, completed 08.08.2015 19:40:12
[error]
[error] /Users/chris/someproject/SomeScalaFile.scala:420: Unmatched closing brace '}' ignored here
[error]         }.groupBy( _._2 ).mapValues {
[error]         ^
