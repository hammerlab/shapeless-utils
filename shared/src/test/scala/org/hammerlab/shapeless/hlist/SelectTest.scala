package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.Suite
import org.hammerlab.test.Cmp

class SelectTest
  extends Suite {

  val foo = Foo(new A2(123), new B2("abc"))

  implicit val cmpA2: Cmp[A2] = Cmp.by(a ⇒ a: A)
  implicit val cmpB2: Cmp[B2] = Cmp.by(b ⇒ b: B)

  test("direct access") {
    ==(!![Select[Foo, A2]].apply(foo), new A2( 123 ))
    ==(!![Select[Foo, B2]].apply(foo), new B2("abc"))
  }

  test("covariant access") {
    ==(!![Select[Foo, A]].apply(foo), A( 123 ))
    ==(!![Select[Foo, B]].apply(foo), B("abc"))
  }

  test("direct syntax") {
    ==(foo.iselect[A2], new A2( 123 ))
    ==(foo.iselect[B2], new B2("abc"))
  }

  test("covariant syntax") {
    ==(foo.iselect[A], A( 123 ))
    ==(foo.iselect[B], B("abc"))
  }

  class A2(m:    Int) extends A(m)
  class B2(t: String) extends B(t)
  case class Foo(a: A2, b: B2)
}
