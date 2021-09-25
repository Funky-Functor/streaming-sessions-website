package com.funkyfunctor.streaming.website
package business

import zio.NonEmptyChunk
import zio.json._

object FormResponseType {
  implicit val okEncoder: JsonEncoder[FormHandledWithoutAnyProblem.type] =
    DeriveJsonEncoder.gen[FormHandledWithoutAnyProblem.type]
  implicit val errorEncoder: JsonEncoder[ErrorHappened] = DeriveJsonEncoder.gen[ErrorHappened]
}

abstract sealed class FormResponseType(msg: String)

case object FormHandledWithoutAnyProblem extends FormResponseType("Form submitted successfully")
case class ErrorHappened(errorMessages: List[String])
    extends FormResponseType("An error has occurred while submitting the form")
