import sbt._

object Versions {
  val zhttpVersion      = "1.0.0.0-RC17"
  val zioJsonVersion    = "0.1.5"
  val zioPreludeVersion = "1.0.0-RC6"
}

object Libraries {
  import Versions._

  val zhttp      = "io.d11"  %% "zhttp"       % zhttpVersion
  val zioJson    = "dev.zio" %% "zio-json"    % zioJsonVersion
  val zioPrelude = "dev.zio" %% "zio-prelude" % zioPreludeVersion
}
