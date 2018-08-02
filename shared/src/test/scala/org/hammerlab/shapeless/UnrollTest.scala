package org.hammerlab.shapeless

import Unroll.Aux
import shapeless.Nat
import shapeless.nat._

class UnrollTest
  extends hammerlab.Suite {
  test("derivations") {
    // Count the levels of [[Seq]]:
    implicitly[Unroll[Seq[Int], Seq]]
    implicitly[Unroll[Seq[Seq[Int]], Seq]]
    implicitly[Unroll[Seq[Seq[Seq[Int]]], Seq]]

    implicitly[Aux[Int, Seq, _0, Int]]
    implicitly[Aux[Seq[Int], Seq, _1, Int]]
    implicitly[Aux[Seq[Seq[Int]], Seq, _2, Int]]
    implicitly[Aux[Seq[Seq[Seq[Int]]], Seq, _3, Int]]

    // Same derivations, but with a type-alias fixing [[Seq]] as the type-constructor
    type U[S] = Unroll[S, Seq]
    type UAux[S, N <: Nat, Out] = Aux[S, Seq, N, Out]

    implicitly[U[Seq[Int]]]
    implicitly[U[Seq[Seq[Int]]]]
    implicitly[U[Seq[Seq[Seq[Int]]]]]

    implicitly[UAux[Int, _0, Int]]
    implicitly[UAux[Seq[Int], _1, Int]]
    implicitly[UAux[Seq[Seq[Int]], _2, Int]]
    implicitly[UAux[Seq[Seq[Seq[Int]]], _3, Int]]
  }
}
