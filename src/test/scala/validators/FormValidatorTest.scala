package com.funkyfunctor.streaming.website
package validators

import validators.FormValidator.ErrorMessage

import com.funkyfunctor.streaming.website.business._
import com.funkyfunctor.streaming.website.errors.{ApplicationError, FormValidationErrors}
import zio.prelude.ZValidation.{Success => ValidationSuccess}
import zio.prelude.ZValidation.{Failure => ValidationFailure}
import zio.test.Assertion._
import zio.test._
import zio.test.environment.TestEnvironment

object FormValidatorTest extends DefaultRunnableSpec {
  override def spec: ZSpec[TestEnvironment, Any] = suite("FormValidator")(
    suite("validate(unvalidatedForm: UnvalidatedForm)") (
      test("should succeed if we pass it an UnvalidatedForm with all" +
        " fields matching the expected formats") {

        val unvalidatedForm = UnvalidatedForm(
          name = Some("First Name, Last Name"),
          telephone = Some("+1 23 456 789"),
          email = Some("name@domain.com"),
          message = Some("hello world!")
        )

        val result = FormValidator.validate(unvalidatedForm)

        val expectedForm = ValidatedForm(
          name = Some("First Name, Last Name"),
          telephone = Some("+1 23 456 789"),
          email = "name@domain.com",
          message = "hello world!"
        )

        assert(result)(isRight[ValidatedForm](equalTo(expectedForm)))
      },
      test("should succeed even if we pass it an empty name") {

        val unvalidatedForm = UnvalidatedForm(
          name = None,
          telephone = Some("+1 23 456 789"),
          email = Some("name@domain.com"),
          message = Some("hello world!")
        )

        val result = FormValidator.validate(unvalidatedForm)

        val expectedForm = ValidatedForm(
          name = None,
          telephone = Some("+1 23 456 789"),
          email = "name@domain.com",
          message = "hello world!"
        )

        assert(result)(isRight[ValidatedForm](equalTo(expectedForm)))
      },
      test("should succeed if we pass it an empty telephone") {

        val unvalidatedForm = UnvalidatedForm(
          name = Some("First Name, Last Name"),
          telephone = None,
          email = Some("name@domain.com"),
          message = Some("hello world!")
        )

        val result = FormValidator.validate(unvalidatedForm)

        val expectedForm = ValidatedForm(
          name = Some("First Name, Last Name"),
          telephone = None,
          email = "name@domain.com",
          message = "hello world!"
        )

        assert(result)(isRight[ValidatedForm](equalTo(expectedForm)))
      },
      test("should fail if we pass it an empty email") {

        val unvalidatedForm = UnvalidatedForm(
          name = Some("First Name, Last Name"),
          telephone = Some("+1 23 456 789"),
          email = None,
          message = Some("hello world!")
        )

        val result = FormValidator.validate(unvalidatedForm)

        assert(result)(isLeft[ApplicationError](isSubtype[FormValidationErrors](anything)))
      },
      test("should fail if we pass it an empty message") {
        val unvalidatedForm = UnvalidatedForm(
          name = Some("First Name, Last Name"),
          telephone = Some("+1 23 456 789"),
          email = Some("name@domain.com"),
          message = None
        )

        val result = FormValidator.validate(unvalidatedForm)

        assert(result)(isLeft[ApplicationError](isSubtype[FormValidationErrors](anything)))
      },
      test("should fail if we pass it an email that doesn't match the expected format") {

        val unvalidatedForm = UnvalidatedForm(
          name = Some("First Name, Last Name"),
          telephone = Some("+1 23 456 789"),
          email = Some("I am not a properly formatted email"),
          message = Some("hello world!")
        )

        val result = FormValidator.validate(unvalidatedForm)

        assert(result)(isLeft[ApplicationError](isSubtype[FormValidationErrors](anything)))
      },
    ),


    suite("validateMessage(msg: Option[String])")(
      test("should succeed if there is a message") {
        val message = "This is a message"

        val result = FormValidator.validateMessage(Some(message))

        assert(result)(isSubtype[ValidationSuccess[ErrorMessage, String]](anything))
      },

      test("should fail if there is no message") {
        val result = FormValidator.validateMessage(None)

        assert(result)(isSubtype[ValidationFailure[ErrorMessage, String]](anything))
      }
    ),


    suite("validateEmail(email: Option[String])")(
      test("should succeed if we pass it a properly formatted email") {
        val properlyFormattedEmail = "name@domain.com"

        val result = FormValidator.validateEmail(Some(properlyFormattedEmail))

        assert(result)(isSubtype[ValidationSuccess[ErrorMessage, String]](anything))
      },
      test("should fail if we pass it an empty email") {
        val result = FormValidator.validateEmail(None)

        assert(result)(isSubtype[ValidationFailure[ErrorMessage, String]](anything))
      },
      test("should fail if we pass it a String that doesn't match the expected format for an email") {
        val randomString = "I am not a properly formatted email"

        val result = FormValidator.validateEmail(Some(randomString))

        assert(result)(isSubtype[ValidationFailure[ErrorMessage, String]](anything))
      }
    )
  )
}
