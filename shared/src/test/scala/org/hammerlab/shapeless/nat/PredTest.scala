package org.hammerlab.shapeless.nat

import org.hammerlab.shapeless.Suite
import shapeless.nat._
import shapeless._

class PredTest
  extends Suite {
  test("summons") {
    Pred.fromSucc[_1]
    !![Pred[_1]]
    !![Pred.Aux[_1, _0]]

    Pred.fromSucc[_2]
    !![_2 =:= Succ[_1]]
    !![Pred[_2]]
    !![Pred.Aux[_2, _1]]

    Pred.fromSucc[_3]
    !![_3 =:= Succ[_2]]
    !![Pred[_3]]
    !![Pred.Aux[_3, _2]]
  }
}
