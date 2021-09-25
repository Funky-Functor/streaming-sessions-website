package com.funkyfunctor.streaming.website
package business

import zio.json._

object UnvalidatedForm {
  implicit val decoder: JsonDecoder[UnvalidatedForm] = DeriveJsonDecoder.gen[UnvalidatedForm]
}

case class UnvalidatedForm(
    name: Option[String],
    telephone: Option[String],
    email: Option[String],
    message: Option[String]
)

case class ValidatedForm(name: Option[String], telephone: Option[String], email: String, message: String)
