package org.hammerlab.shapeless.hlist

import shapeless._

class FlattenApplyMaskTest
  extends FlattenTestI
     with utils {

  // Test summons
  !![Flatten.Aux[AA, String :: ⊥]]
  !![Flatten.Aux[AA :: ⊥, String :: ⊥]]
  !![Flatten.Aux[AA :: BB :: ⊥, String :: String :: ⊥]]
  !![Flatten.Aux[CC, String :: String :: ⊥]]

  test("flatten") {
    val aa = new AA("11")
    val bb = BB("22")
    val cc = CC(aa, bb)

    check(aa,      "11" :: ⊥)
    check(aa :: ⊥, "11" :: ⊥)
    check(     cc, "11" :: "22" :: ⊥)
  }
}
