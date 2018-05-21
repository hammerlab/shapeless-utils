package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.{ Suite, ⊥ }
import shapeless._

case class AA(override val toString: String)
object AA {
  // An apply(String) method like this one inhibits auto-derivation of [[Generic]] / [[Flatten]]
  def apply(s: String): Int = ???

  // Manually provide one, and verify in tests that downstream derivations work as expected
  implicit def flatten: Flatten.Aux[AA, String :: ⊥] =
    Flatten.make {
      _.toString :: HNil
    }
}
case class BB(override val toString: String)
case class CC(aa: AA, bb: BB)

class FlattenApplyMaskTest
  extends FlattenTestI {
  implicitly[Flatten.Aux[AA, String :: ⊥]]
  implicitly[Flatten.Aux[AA :: ⊥, String :: ⊥]]
  implicitly[Flatten.Aux[AA :: BB :: ⊥, String :: String :: ⊥]]
  implicitly[Flatten.Aux[CC, String :: String :: ⊥]]

  test("summons") {
    val aa = new AA("11")
    val bb = BB("22")
    val cc = CC(aa, bb)

    check(aa,      "11" :: ⊥)
    check(aa :: ⊥, "11" :: ⊥)
    check(     cc, "11" :: "22" :: ⊥)
  }
}
