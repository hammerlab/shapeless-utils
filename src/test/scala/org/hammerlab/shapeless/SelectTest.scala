package org.hammerlab.shapeless

import org.hammerlab.test.Suite
import Utils._

class SelectTest
  extends Suite {

  import SelectTest._

  val foo = Foo(new A2(123), new B2("abc"))

  test("direct access") {
    implicitly[Select[Foo, A2]].apply(foo) should be(new A2(123))
    implicitly[Select[Foo, B2]].apply(foo) should be(new B2("abc"))
  }

  test("covariant access") {
    implicitly[Select[Foo, A]].apply(foo) should be(A(123))
    implicitly[Select[Foo, B]].apply(foo) should be(B("abc"))
  }

  test("direct syntax") {
    foo.iselect[A2] should be(new A2(123))
    foo.iselect[B2] should be(new B2("abc"))
  }

  test("covariant syntax") {
    foo.iselect[A] should be(A(123))
    foo.iselect[B] should be(B("abc"))
  }
}

object SelectTest {
  class A2(m: Int) extends A(m)
  class B2(t: String) extends B(t)
  case class Foo(a: A2, b: B2)
}

