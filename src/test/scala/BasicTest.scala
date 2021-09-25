package com.funkyfunctor.streaming.website

import zio.test.Assertion._
import zio.test._

object BasicTest extends DefaultRunnableSpec {
  val xs = Vector(0, 1, 2, 5)

  override def spec: ZSpec[_root_.zio.test.environment.TestEnvironment, Any] = test("Fourth value is equal to 5") {
    assert(xs)(hasAt(3)(equalTo(5)))
  }
}
