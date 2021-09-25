package com.funkyfunctor.streaming.website
package handlers

import zhttp.http.{Header, Response, Status}

object RedirectionHandler {
  def handleRequest(redirectionURL: String): Response.HttpResponse[Any, Nothing] = //Redirect to my Discord server's invitation
    Response.http(
      status = Status.MOVED_PERMANENTLY,
      headers = List(Header.custom("Location", redirectionURL))
    )
}
