package org.hammerlab.shapeless.coproduct

import org.hammerlab.test.Suite

object SingletonTest {

  /**
   * Sample sealed hierarchy where all implementations are [[Int]] [[Singleton]]s
   */
  sealed trait Foo

  case class A(n: Int) extends Foo

  sealed trait Bar extends Foo
  case class B(n: Int) extends Bar
  case class C(n: Int) extends Bar
  case class D(n: Int) extends Bar
}

class SingletonTest
  extends Suite {

  import SingletonTest._

  import hammerlab.shapeless._

  /**
   * Hang a multiplication ([[*]]) operator off any [[Int]]-[[Singleton]], in this case [[Bar]] and its subclasses.
   */
  implicit class Ops[U](t: U) {
    def *(n: Int)(implicit e: Singleton[U, Int]): U = t.map(_ * n)
  }

  test("coproduct") {
    A(2) * 10 should be(A(20))
    B(2) * 10 should be(B(20))
    C(2) * 10 should be(C(20))
    D(2) * 10 should be(D(20))

    val foo: Foo = A(2)
    foo * (10) should be(A(20))

    val bar: Bar = B(2)
    bar * (10) should be(B(20))
  }
}
