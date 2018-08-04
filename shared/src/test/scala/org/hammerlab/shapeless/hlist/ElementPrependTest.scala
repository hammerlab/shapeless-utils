package org.hammerlab.shapeless.hlist

import shapeless._

class ElementPrependTest
  extends hammerlab.Suite {
  test("atoms") {
    !![
      ElementPrepend[
        Int,
        String
      ]
    ]
    .apply(
      123,
      "abc"
    ) should be(
      123 :: "abc" :: HNil
    )
  }

  test("lists") {
    !![
      ElementPrepend[
        Int,
        String :: HNil
      ]
    ]
    .apply(
      123,
      "abc" :: HNil
    ) should be(
      123 :: "abc" :: HNil
    )
  }
}
