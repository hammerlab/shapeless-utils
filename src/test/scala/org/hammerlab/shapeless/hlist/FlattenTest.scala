package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.Utils.{ A, B, C, D, E, aa, b, c, d, e }
import org.hammerlab.shapeless.⊥
import org.hammerlab.test.Suite
import shapeless._

class FlattenTest
  extends Suite {

  test("summons") {
    implicitly[Flatten[A]].apply(aa) should be(123 :: ⊥)
    implicitly[Flatten[B]].apply(b) should be("abc" :: ⊥)
    implicitly[Flatten[C]].apply(c) should be(123 :: "abc" :: ⊥)
    implicitly[Flatten[D]].apply(d) should be(true :: ⊥)
    implicitly[Flatten[E]].apply(e) should be(123 :: "abc" :: true :: 456 :: ⊥)

    implicitly[Flatten[String]].apply("abc") should be("abc" :: ⊥)

    implicitly[Flatten[(Int :: ⊥) :: ⊥]].apply((111 :: ⊥) :: ⊥) should be(111 :: ⊥)

    implicitly[Flatten[(Int :: (String :: ⊥) :: ⊥) :: ⊥]].apply((111 :: ("aaa" :: ⊥) :: ⊥) :: ⊥) should be(111 :: "aaa" :: ⊥)

    implicitly[Flatten[(A :: ⊥) :: ⊥]].apply((aa :: ⊥) :: ⊥) should be(123 :: ⊥)

    implicitly[Flatten[(A :: (B :: ⊥) :: ⊥) :: ⊥]].apply((aa :: (b :: ⊥) :: ⊥) :: ⊥) should be(123 :: "abc" :: ⊥)

    implicitly[
      Flatten[
        (Int :: ⊥) ::
        (
          String ::
          (Boolean :: ⊥) ::
          ⊥
        ) ::
        (A :: (B :: ⊥) :: ⊥) ::
        ⊥
      ]
    ]
    .apply(compound) should be(flattenedCompound)
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
    aa.flatten should be(123 :: ⊥)
    b.flatten should be("abc" :: ⊥)
    c.flatten should be(123 :: "abc" :: ⊥)
    d.flatten should be(true :: ⊥)
    e.flatten should be(123 :: "abc" :: true :: 456 :: ⊥)

    compound.flatten should be(flattenedCompound)
  }
}
