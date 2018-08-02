package org.hammerlab.shapeless.hlist

import hammerlab.shapeless.⊥
import shapeless.{ ::, HNil }

trait utils {
  case class AA(override val toString: String)
  object AA {
    // An apply(String) method like this one inhibits auto-derivation of [[Generic]] / [[Flatten]]
    def apply(s: String): Int = ???

    // Manually provide one, and verify in tests that downstream derivations work as expected
    implicit def flatten: Flatten.Aux[AA, String :: ⊥] =
      Flatten.make {
        _.toString :: HNil
      }

    implicit val toGeneric: ToGeneric.Aux[AA, String :: ⊥] =
      ToGeneric.aux {
        _.toString :: HNil
      }
  }
  case class BB(override val toString: String)
  case class CC(aa: AA, bb: BB)
}
