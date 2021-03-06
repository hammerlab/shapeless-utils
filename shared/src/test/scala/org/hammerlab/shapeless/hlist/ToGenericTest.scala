package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.Suite
import org.hammerlab.test.Cmp
import shapeless._

trait ToGenericTestI
  extends Suite {
  def check[T, L <: HList, D](t: T, l: L)(implicit g: ToGeneric.Aux[T, L], cmp: Cmp.Aux[L, D]) =
    ==(g(t), l)
}

class ToGenericTest
  extends ToGenericTestI {

  !![ToGeneric[A]]
  !![ToGeneric.Aux[A, Int :: ⊥]]

  !![ToGeneric[C]]
  !![ToGeneric.Aux[C, A :: B :: ⊥]]

  test("summons") {
    check(_a, 123 :: ⊥)
    check( c,  _a :: b :: ⊥)
  }
}
