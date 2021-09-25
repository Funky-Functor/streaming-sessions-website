package com.funkyfunctor.streaming.website
import zhttp.service.Server
import zio._

object Main extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8080, Router.routes).exitCode
}
