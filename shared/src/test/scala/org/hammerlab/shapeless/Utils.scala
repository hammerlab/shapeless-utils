package org.hammerlab.shapeless

import shapeless.LabelledGeneric

trait Utils {
  case class A(n: Int)
  case class B(s: String)
  case class C(a: A, b: B)
  case class D(b: Boolean)
  case class E(c: C, d: D, a: A, a2: A)
  case class F(e: E)

  val aa = A(123)  // named with two a's to avoid collision with [[Matchers.a]]
  val b = B("abc")
  val c = C(aa, b)
  val d = D(true)
  val e = E(c, d, A(456), A(789))
  val f = F(e)

  val lga = LabelledGeneric[A]
  val lgb = LabelledGeneric[B]
  val lgc = LabelledGeneric[C]
}

object Utils extends Utils
