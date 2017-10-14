package org.hammerlab.shapeless

import org.hammerlab.test.Suite
import shapeless._
import Utils._

class FlattenedTest
  extends Suite {
  test("summons") {
    implicitly[Flattened[A]].apply(aa) should be(123 :: HNil)
    implicitly[Flattened[B]].apply(b) should be("abc" :: HNil)
    implicitly[Flattened[C]].apply(c) should be(123 :: "abc" :: HNil)
    implicitly[Flattened[E]].apply(e) should be(123 :: "abc" :: true :: 456 :: HNil)
  }

  test("ops") {
    import Flattened._
    aa.flatten should be(123 :: HNil)
    b.flatten should be("abc" :: HNil)
    c.flatten should be(123 :: "abc" :: HNil)
    e.flatten should be(123 :: "abc" :: true :: 456 :: HNil)
  }
}
