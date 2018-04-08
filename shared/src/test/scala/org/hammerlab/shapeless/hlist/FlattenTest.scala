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

class FlattenTest
  extends Suite {

  implicitly[Flatten.Aux[AA, String :: ⊥]]
  implicitly[Flatten.Aux[AA :: ⊥, String :: ⊥]]
  implicitly[Flatten.Aux[AA :: BB :: ⊥, String :: String :: ⊥]]
  implicitly[Flatten.Aux[CC, String :: String :: ⊥]]

  test("summons") {

    def check[T, L <: HList](t: T, l: L)(implicit f: Flatten.Aux[T, L]) =
      f(t) should be(l)

    check(_a,  123  ::   ⊥ )
    check( b, "abc" ::   ⊥ )
    check( c,  123  :: "abc" :: ⊥ )
    check( d, true  ::   ⊥ )
    check( e,  123  :: "abc" :: true :: 456 :: 789 :: ⊥ )

    check((111 :: ⊥) :: ⊥, 111 :: ⊥)

    val aa = new AA("11")
    val bb = BB("22")
    val cc = CC(aa, bb)

    check(aa,      "11" :: ⊥)
    check(aa :: ⊥, "11" :: ⊥)
    check(     cc, "11" :: "22" :: ⊥)

    check(
      (111 :: ( "aaa" :: ⊥) :: ⊥) :: ⊥,
       111 ::   "aaa" :: ⊥
    )

    check(
      (_a :: ⊥ ) :: ⊥,
      123 :: ⊥
    )

    check(
      (_a :: (b :: ⊥) :: ⊥) :: ⊥,
      123 :: "abc" :: ⊥
    )

    check(compound, flattenedCompound)
  }

  val compound =
    (111 :: ⊥) ::
    (
      "aaa" ::
      (true :: ⊥) ::
      ⊥
    ) ::
    (aa :: (b :: ⊥) :: ⊥) ::
    ⊥

  val flattenedCompound =
    111 ::
    "aaa" ::
    true ::
    123 ::
    "abc" ::
    ⊥

  test("ops") {
    import Flatten._
    _a.flatten should be(123 :: ⊥)
     b.flatten should be("abc" :: ⊥)
     c.flatten should be(123 :: "abc" :: ⊥)
     d.flatten should be(true :: ⊥)
     e.flatten should be(123 :: "abc" :: true :: 456 :: 789 :: ⊥)

    compound.flatten should be(flattenedCompound)
  }
}
