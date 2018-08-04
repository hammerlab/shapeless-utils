package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.Suite
import shapeless._

class CartesianTest
  extends Suite {
  test("products") {
    !![
      Cartesian[
        Int :: HNil,
        String :: HNil
      ]
    ]
    .apply(
      1 :: HNil,
      "a" :: HNil
    ) should be(
      (1 :: "a" :: HNil) ::
      HNil
    )

    !![
      Cartesian[
        Int :: Boolean :: HNil,
        String :: HNil
      ]
    ]
    .apply(
      1 :: true :: HNil,
      "a" :: HNil
    ) should be(
      (1 :: "a" :: HNil) ::
      (true :: "a" :: HNil) ::
      HNil
    )

    !![
      Cartesian[
        Int :: Boolean :: HNil,
        String :: Long ::  HNil
      ]
    ]
    .apply(
      1 :: true :: HNil,
      "a" :: 3L :: HNil
    ) should be(
      (1 :: "a" :: HNil) ::
      (1 :: 3L :: HNil) ::
      (true :: "a" :: HNil) ::
      (true :: 3L :: HNil) ::
      HNil
    )

    !![
      Cartesian[
        Int :: HNil,
        HNil
      ]
    ]
    .apply(
      1 :: HNil,
      HNil
    ) should be(
      HNil
    )

    !![
      Cartesian[
        HNil,
        Int :: HNil
      ]
    ]
    .apply(
      HNil,
      1 :: HNil
    ) should be(
      HNil
    )
  }
}
