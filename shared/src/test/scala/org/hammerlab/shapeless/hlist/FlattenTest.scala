package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.{ Suite, ⊥ }
import shapeless._

trait FlattenTestI
  extends Suite {
  def check[T, L <: HList](t: T, l: L)(implicit f: Flatten.Aux[T, L]) =
    f(t) should be(l)
}

class FlattenTest
  extends FlattenTestI {

  test("summons") {

    def check[T, L <: HList](t: T, l: L)(implicit f: Flatten.Aux[T, L]) =
      f(t) should be(l)

    check(_a,  123  ::   ⊥ )
    check( b, "abc" ::   ⊥ )
    check( c,  123  :: "abc" :: ⊥ )
    check( d, true  ::   ⊥ )
    check( e,  123  :: "abc" :: true :: 456 :: 789 :: ⊥ )

    check((111 :: ⊥) :: ⊥, 111 :: ⊥)

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
