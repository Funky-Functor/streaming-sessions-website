package com.funkyfunctor.streaming.website
package validators

import business.{UnvalidatedForm, ValidatedForm}
import errors.{ApplicationError, FormValidationErrors}

import zio.prelude._

object FormValidator {
  case class ErrorMessage(msg: String)

  val EMAIL_VALIDATION_REGEX =
    """^(?=.{1,64}@)[\p{L}0-9_-]+(\.[\p{L}0-9_-]+)*@[^-][\p{L}0-9-]+(\.[\p{L}0-9-]+)*(\.[\p{L}]{2,})$"""

  def validate(unvalidatedForm: UnvalidatedForm): Either[ApplicationError, ValidatedForm] = {
    for {
      name      <- Validation.succeed(unvalidatedForm.name)
      telephone <- Validation.succeed(unvalidatedForm.telephone)
      email     <- validateEmail(unvalidatedForm.email)
      message   <- validateMessage(unvalidatedForm.message)
    } yield ValidatedForm(
      name = name,
      telephone = telephone,
      email = email,
      message = message
    )
  }.toEitherWith { errors =>
    FormValidationErrors(errors.map(_.msg))
  }

  def validateMessage(msg: Option[String]): Validation[ErrorMessage, String] =
    Validation.fromOptionWith(ErrorMessage("Missing message field"))(msg)

  def validateEmail(email: Option[String]): Validation[ErrorMessage, String] = {
    //First we validate we have an email defined
    Validation
      .fromOptionWith(ErrorMessage("Missing email field"))(email)

      //Secondly, we want to make sure the string is a proper email
      .flatMap { emailString =>
        Validation.fromPredicateWith(ErrorMessage("The provided email does not match the validation regex"))(
          emailString
        )(
          _.matches(EMAIL_VALIDATION_REGEX)
        )
      }
  }

}
