package org.hammerlab.shapeless.coproduct

import shapeless._

class OptionsTest
  extends hammerlab.Suite {
  test("summons") {
    !![Options[Option[Int]]]
    !![Options[Option[_]]]

    !![Options.Aux[Option[Int], None.type :: Some[Int] :: HNil]]
    !![Options.Aux[Option[_], None.type :: Some[Any] :: HNil]]
  }
}
