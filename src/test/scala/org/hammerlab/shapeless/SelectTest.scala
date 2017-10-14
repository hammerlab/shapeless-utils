package org.hammerlab.shapeless

import org.hammerlab.test.Suite
import Utils._

class SelectTest
  extends Suite {

  import SelectTest._

  val foo = Foo(new A2(123), new B2("abc"))

  test("direct access") {
    implicitly[Select[Foo, A2]].apply(foo) should be(new A2(123))
  }

  test("covariant access") {
    implicitly[Select[Foo, A]].apply(foo) should be(A(123))
  }

  test("direct syntax") {
    foo.iselect[A2] should be(new A2(123))
  }

  test("covariant syntax") {
    foo.iselect[A] should be(A(123))
  }
}

object SelectTest {
  class A2(m: Int) extends A(m)
  class B2(t: String) extends B(t)
  case class Foo(a: A2, b: B2)
}

