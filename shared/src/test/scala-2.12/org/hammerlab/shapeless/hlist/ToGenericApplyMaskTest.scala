package org.hammerlab.shapeless.hlist

import org.hammerlab.test.Cmp
import shapeless._

class ToGenericApplyMaskTest
  extends ToGenericTestI
     with utils {

  !![ToGeneric[AA]]
  !![ToGeneric.Aux[AA, String :: ⊥]]

  !![ToGeneric[BB]]
  !![ToGeneric.Aux[BB, String :: ⊥]]

  !![ToGeneric[CC]]
  !![ToGeneric.Aux[CC, AA :: BB :: ⊥]]

  test("summons") {

    val aa = new AA("abc")
    val bb = BB("def")
    val cc = CC(aa, bb)

    implicit val cmpAA: Cmp[AA] = Cmp.by[String, AA](_.toString)

    check(aa, "abc" :: ⊥)
    check(bb, "def" :: ⊥)
    check(cc,   aa  :: bb :: ⊥)
  }
}
