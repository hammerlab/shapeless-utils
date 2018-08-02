package org.hammerlab.shapeless.nesting.seq

import org.hammerlab.shapeless.Suite
import org.hammerlab.shapeless.nesting.seq.Repd.Aux
import shapeless.nat._

class RepdTest
  extends Suite {
  test("derivations") {
    !![Repd[Int]]
    !![Aux[Int, _0, Int]]

    !![Repd[Seq[Int]]]
    !![Aux[Seq[Int], _1, Seq[Int]]]
    !![Aux[Vector[Int], _1, Seq[Int]]]
    !![Aux[Range, _1, Seq[Int]]]
    !![Aux[Range.Inclusive, _1, Seq[Int]]]
    !![Aux[Range, _1, Seq[Int]]]
    !![Repd[Range.Inclusive]]
    !![Repd[Range]]

    !![Repd[Seq[Seq[Int]]]]
    !![Aux[Seq[Seq[Int]], _2, Seq[Seq[Int]]]]
    !![Aux[Vector[IndexedSeq[Int]], _2, Seq[Seq[Int]]]]

    !![Repd[Seq[Seq[Seq[Int]]]]]
    !![Aux[Seq[Seq[Seq[Int]]], _3, Seq[Seq[Seq[Int]]]]]
  }
}
