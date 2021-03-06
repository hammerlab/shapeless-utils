package org.hammerlab.shapeless.nesting.seq

import org.hammerlab.shapeless.Suite
import shapeless.nat._

class NestedTest
  extends Suite {

  import Nested.Aux

  test("derivations") {
    !![Nested[Int]]
    !![Aux[Int, _0, Int]]

    !![Nested[Seq[Int]]]
    !![Aux[Seq[Int], _1, Seq[Int]]]
    !![Aux[Vector[Int], _1, Seq[Int]]]
    !![Aux[Range, _1, Seq[Int]]]
    !![Aux[Range.Inclusive, _1, Seq[Int]]]
    !![Aux[Range, _1, Seq[Int]]]
    !![Nested[Range.Inclusive]]
    !![Nested[Range]]

    !![Nested[Seq[Seq[Int]]]]
    !![Aux[Seq[Seq[Int]], _2, Seq[Seq[Int]]]]
    !![Aux[Vector[IndexedSeq[Int]], _2, Seq[Seq[Int]]]]

    !![Nested[Seq[Seq[Seq[Int]]]]]
    !![Aux[Seq[Seq[Seq[Int]]], _3, Seq[Seq[Seq[Int]]]]]

    val input =
      List(
        Vector(
          IndexedSeq( 1,  2,  3),
          IndexedSeq( 4,  5,  6)
        ),
        Vector(
          IndexedSeq( 7,  8,  9),
          IndexedSeq(10, 11, 12)
        )
      )

    val output =
      Seq(
        Seq(
          Seq( 1,  2,  3),
          Seq( 4,  5,  6)
        ),
        Seq(
          Seq( 7,  8,  9),
          Seq(10, 11, 12)
        )
      )

    val ev = !![Nested[List[Vector[IndexedSeq[Int]]]]]
    ==(ev(input), output)

    val aux = !![Aux[List[Vector[IndexedSeq[Int]]], _3, Seq[Seq[Seq[Int]]]]]
    ==(aux(input), output)
  }
}
