package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.⊥
import shapeless._

class ToGenericApplyMaskTest
  extends ToGenericTestI
     with utils {

  implicitly[ToGeneric[AA]]
  implicitly[ToGeneric.Aux[AA, String :: ⊥]]

  implicitly[ToGeneric[BB]]
  implicitly[ToGeneric.Aux[BB, String :: ⊥]]

  implicitly[ToGeneric[CC]]
  implicitly[ToGeneric.Aux[CC, AA :: BB :: ⊥]]

  test("summons") {

    val aa = new AA("abc")
    val bb = BB("def")
    val cc = CC(aa, bb)

    check(aa, "abc" :: ⊥)
    check(bb, "def" :: ⊥)
    check(cc,   aa  :: bb :: ⊥)
  }
}
