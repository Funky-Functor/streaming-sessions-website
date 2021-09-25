package com.funkyfunctor.streaming.website
package business

import business.UnvalidatedForm.decoder

import zio.json._
import zio.test.Assertion._
import zio.test._

object UnvalidatedFormTest extends DefaultRunnableSpec {
  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = suite("UnvalidatedForm")(
    test("can convert a properly formatted String to UnvalidatedForm") {
      val unvalidatedForm = UnvalidatedForm(
        name = Some("First Name, Last Name"),
        telephone = Some("+1 23 456 789"),
        email = Some("name@domain.com"),
        message = Some("hello world!")
      )

      implicit val encoder: JsonEncoder[UnvalidatedForm] = DeriveJsonEncoder.gen[UnvalidatedForm]

      val jsonString = unvalidatedForm.toJson

      val result = jsonString.fromJson[UnvalidatedForm]

      assert(result)(isRight[UnvalidatedForm](equalTo(unvalidatedForm)))
    },
    test("returns an error if we try to convert a bad string into an UnvalidatedForm") {
      val randomString = "I am not a String that can be converted into an UnvalidatedForm"

      val result = randomString.fromJson[UnvalidatedForm]

      assert(result)(isLeft)
    }
  )
}
