package org.hammerlab.shapeless.coproduct

import hammerlab.shapeless._
import org.hammerlab.Suite

object SingletonTest {

  /**
   * Sample sealed hierarchy where all implementations are [[Int]] [[Singleton]]s
   */
  sealed trait Foo

    case class   A(n: Int) extends Foo

  sealed trait Bar         extends Foo
    case class   B(n: Int) extends Bar
    case class   C(n: Int) extends Bar
    case class   D(n: Int) extends Bar

  /**
   * Hang a multiplication ([[*]]) operator off any [[Int]]-[[Singleton]], in this case [[Bar]] and its subclasses.
   */
  implicit class Ops[U](val t: U) extends AnyVal {
    def *(n: Int)(implicit e: Singleton[U, Int]): U = t map(_ * n)
  }
}

class SingletonTest
  extends Suite {

  import SingletonTest._

  test("coproduct") {
    // mapping preserves type
    val a: A = A(2) * 10

    ==(A(2) * 10, A(20))
    ==(B(2) * 10, B(20))
    ==(C(2) * 10, C(20))
    ==(D(2) * 10, D(20))

    val foo: Foo = A(2)
    ==(foo * 10, A(20))

    val bar: Bar = B(2)
    ==(bar * 10, B(20))

    // test summoning
    val _: Singleton[Foo, Int] = Singleton[Foo, Int]
  }
}
