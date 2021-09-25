package com.funkyfunctor.streaming.website
package handlers

import business._
import errors._
import validators.FormValidator

import zhttp.http._
import zio.json._

object ContactFormRequestHandler {
  def handleRequest(request: Request) = {
    val finalResult = for {
      contentAsString <- getContentString(request)
      contentAsJson   <- getContentAsJson(contentAsString)
      validatedForm   <- FormValidator.validate(contentAsJson)
      result          <- postValidatedForm(validatedForm)
    } yield result

    handleFinalResult(finalResult)
  }

  def getContentString(request: Request): Either[EmptyBodyError.type, String] = request.getBodyAsString match {
    case Some(body) => Right(body)
    case None       => Left(EmptyBodyError)
  }

  def getContentAsJson(body: String): Either[JsonParsingError, UnvalidatedForm] = body.fromJson[UnvalidatedForm] match {
    case Left(errorMessage) => Left(JsonParsingError(errorMessage))
    case Right(form)        => Right(form)
  }

  def postValidatedForm(form: ValidatedForm): Either[ApplicationError, Unit] = {
    Left(ValidatedFormSubmissionProblem("Validated form submission has not been implemented yet"))
  }

  def handleFinalResult(finalResult: Either[ApplicationError, Unit]): UResponse = {
    finalResult match {
      case Right(_) => Response.jsonString(FormHandledWithoutAnyProblem.toJson)
      case Left(error) => {
        val errorHappened = ErrorHappened(error.errors.toList)
        Response.jsonString(errorHappened.toJson)
      }
    }
  }
}
