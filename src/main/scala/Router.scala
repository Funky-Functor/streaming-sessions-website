package com.funkyfunctor.streaming.website

import handlers.{ContactFormRequestHandler, RedirectionHandler, StaticResourceHandler}

import zhttp.http._
import zio.blocking.Blocking

import java.io.IOException

object Router {
  val routes: Http[Any, Nothing, Request, Response[Blocking, IOException]] = Http.collect[Request] {

    //TODO Make the redirect configurable
    case Method.GET -> Root / "discord" => RedirectionHandler.handleRequest("https://discord.gg/Y7t98BHK9E")

    case request @ (Method.POST -> Root / "contactForm") => ContactFormRequestHandler.handleRequest(request)

    case Method.GET -> Root / path => StaticResourceHandler.handleRequest(path)
  }
}
