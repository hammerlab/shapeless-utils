package org.hammerlab.shapeless

import shapeless.LabelledGeneric

object Utils {
  case class A(n: Int)
  case class B(s: String)
  case class C(a: A, b: B)
  case class D(b: Boolean)
  case class E(c: C, d: D, a: A)

  val aa = A(123)
  val b = B("abc")
  val c = C(aa, b)
  val d = D(true)
  val e = E(c, d, A(456))

  val lga = LabelledGeneric[A]
  val lgb = LabelledGeneric[B]
  val lgc = LabelledGeneric[C]
}
