package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.{ Suite, ⊥ }
import shapeless._

trait ToGenericTestI
  extends Suite {
  def check[T, L <: HList](t: T, l: L)(implicit g: ToGeneric.Aux[T, L]) = {
    g(t) should be(l)
  }
}

class ToGenericTest
  extends ToGenericTestI {

  implicitly[ToGeneric[A]]
  implicitly[ToGeneric.Aux[A, Int :: ⊥]]

  implicitly[ToGeneric[C]]
  implicitly[ToGeneric.Aux[C, A :: B :: ⊥]]

  test("summons") {
    check(_a, 123 :: ⊥)
    check( c,  _a :: b :: ⊥)
  }
}
