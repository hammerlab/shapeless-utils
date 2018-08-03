package org.hammerlab.shapeless.nesting

import org.hammerlab.shapeless.Suite

class UnrollTest
  extends Suite {
  test("basic summons") {
    !![Unroll[Int, Seq]]
    !![Unroll[Seq[Int], Seq]]
    !![Unroll[Seq[Seq[Int]], Seq]]
    !![Unroll[Seq[Seq[Seq[Int]]], Seq]]
  }

  import Unroll.Aux

  test("count levels") {
    !![Aux[Int, Seq, _0, Int]]
    !![Aux[Seq[Int], Seq, _1, Int]]
    !![Aux[Seq[Seq[Int]], Seq, _2, Int]]
    !![Aux[Seq[Seq[Seq[Int]]], Seq, _3, Int]]
  }

  // Use a type-alias to fix [[Seq]] as the type-constructor
  type U[S] = Unroll[S, Seq]
  type UAux[S, N <: Nat, Out] = Aux[S, Seq, N, Out]

  test("summon through type-alias") {
    !![U[Seq[Int]]]
    !![U[Seq[Seq[Int]]]]
    !![U[Seq[Seq[Seq[Int]]]]]
  }

  test("count through type-alias") {
    !![UAux[Int, _0, Int]]
    !![UAux[Seq[Int], _1, Int]]
    !![UAux[Seq[Seq[Int]], _2, Int]]
    !![UAux[Seq[Seq[Seq[Int]]], _3, Int]]
  }
}
