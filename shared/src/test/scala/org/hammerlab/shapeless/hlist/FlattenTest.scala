package org.hammerlab.shapeless.hlist

import hammerlab.shapeless.⊥
import org.hammerlab.shapeless.Suite
import org.hammerlab.test.Cmp
import shapeless._

trait FlattenTestI
  extends Suite {
  def check[T, L <: HList, D](t: T, l: L)(implicit f: Flatten.Aux[T, L], cmp: Cmp.Aux[L, D]) =
    ==(f(t), l)
}

class FlattenTest
  extends FlattenTestI {

  test("summons") {

    def check[T, L <: HList, D](t: T, l: L)(implicit f: Flatten.Aux[T, L], cmp: Cmp.Aux[L, D]) =
      ==(f(t), l)

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
    (_a :: (b :: ⊥) :: ⊥) ::
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
    ===(_a.flatten, 123 :: ⊥)
    ===( b.flatten, "abc" :: ⊥)
    ===( c.flatten, 123 :: "abc" :: ⊥)
    ===( d.flatten, true :: ⊥)
    ===( e.flatten, 123 :: "abc" :: true :: 456 :: 789 :: ⊥)

    ===(compound.flatten, flattenedCompound)
  }
}
