package org.hammerlab.shapeless.nat

import hammerlab.shapeless._
import shapeless.nat._
import shapeless._

class PredTest
  extends hammerlab.Suite {
  test("summons") {
    Pred.fromSucc[_1]
    implicitly[Pred[_1]]
    implicitly[Pred.Aux[_1, _0]]

    Pred.fromSucc[_2]
    implicitly[_2 =:= Succ[_1]]
    implicitly[Pred[_2]]
    implicitly[Pred.Aux[_2, _1]]

    Pred.fromSucc[_3]
    implicitly[_3 =:= Succ[_2]]
    implicitly[Pred[_3]]
    implicitly[Pred.Aux[_3, _2]]
  }
}
