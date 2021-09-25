package com.funkyfunctor.streaming.website
package errors

import zio.NonEmptyChunk

abstract sealed class ApplicationError(val errors: NonEmptyChunk[String])

case object EmptyBodyError
    extends ApplicationError(NonEmptyChunk.single("Could not find a body content for the request"))
case class JsonParsingError(msg: String) extends ApplicationError(NonEmptyChunk.single(msg))
case class FormValidationErrors(override val errors: NonEmptyChunk[String]) extends ApplicationError(errors)
case class ValidatedFormSubmissionProblem(msg: String) extends ApplicationError(NonEmptyChunk.single(msg))
