package com.funkyfunctor.streaming.website
package handlers

import zhttp.http.{HttpData, Response}
import zio.blocking.Blocking
import zio.stream.ZStream

import java.io.IOException

object StaticResourceHandler {
  def content(uri: String) = HttpData.fromStream {
    ZStream.fromInputStream(getClass.getClassLoader.getResourceAsStream(uri))
  }

  def handleRequest(uri: String): Response.HttpResponse[Blocking, IOException] = Response.http(content = content(uri))
}
