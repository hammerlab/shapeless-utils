package org.hammerlab.shapeless.nesting

import org.hammerlab.shapeless.nesting.Unroll.Aux
import shapeless.Nat
import shapeless.nat._

class UnrollTest
  extends hammerlab.Suite {
  test("basic summons") {
    implicitly[Unroll[Seq[Int], Seq]]
    implicitly[Unroll[Seq[Seq[Int]], Seq]]
    implicitly[Unroll[Seq[Seq[Seq[Int]]], Seq]]
  }

  test("count levels") {
    implicitly[Aux[Int, Seq, _0, Int]]
    implicitly[Aux[Seq[Int], Seq, _1, Int]]
    implicitly[Aux[Seq[Seq[Int]], Seq, _2, Int]]
    implicitly[Aux[Seq[Seq[Seq[Int]]], Seq, _3, Int]]
  }

  // Use a type-alias to fix [[Seq]] as the type-constructor
  type U[S] = Unroll[S, Seq]
  type UAux[S, N <: Nat, Out] = Aux[S, Seq, N, Out]

  test("summon through type-alias") {
    implicitly[U[Seq[Int]]]
    implicitly[U[Seq[Seq[Int]]]]
    implicitly[U[Seq[Seq[Seq[Int]]]]]
  }

  test("count through type-alias") {
    implicitly[UAux[Int, _0, Int]]
    implicitly[UAux[Seq[Int], _1, Int]]
    implicitly[UAux[Seq[Seq[Int]], _2, Int]]
    implicitly[UAux[Seq[Seq[Seq[Int]]], _3, Int]]
  }
}
