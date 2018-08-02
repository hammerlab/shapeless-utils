package org.hammerlab.shapeless.hlist

import hammerlab.shapeless.⊥
import shapeless._

class FlattenApplyMaskTest
  extends FlattenTestI
     with utils {

  // Test summons
  implicitly[Flatten.Aux[AA, String :: ⊥]]
  implicitly[Flatten.Aux[AA :: ⊥, String :: ⊥]]
  implicitly[Flatten.Aux[AA :: BB :: ⊥, String :: String :: ⊥]]
  implicitly[Flatten.Aux[CC, String :: String :: ⊥]]

  test("flatten") {
    val aa = new AA("11")
    val bb = BB("22")
    val cc = CC(aa, bb)

    check(aa,      "11" :: ⊥)
    check(aa :: ⊥, "11" :: ⊥)
    check(     cc, "11" :: "22" :: ⊥)
  }
}
