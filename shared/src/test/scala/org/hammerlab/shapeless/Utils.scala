package org.hammerlab.shapeless

import shapeless.LabelledGeneric

trait Utils {
  case class A(n: Int)
  case class B(s: String)
  case class C(a: A, b: B)
  case class D(b: Boolean)
  case class E(c: C, d: D, a: A, a2: A)
  case class F(e: E)

  val _a = A(123)  // prefixed by "_" avoid collision with [[Matchers.a]]
  val  b = B("abc")
  val  c = C(_a, b)
  val  d = D(true)
  val  e = E(c, d, A(456), A(789))
  val  f = F(e)

  val lga = LabelledGeneric[A]
  val lgb = LabelledGeneric[B]
  val lgc = LabelledGeneric[C]
}
