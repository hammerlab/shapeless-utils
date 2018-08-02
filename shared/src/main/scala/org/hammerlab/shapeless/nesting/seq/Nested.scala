package org.hammerlab.shapeless.nesting.seq

import shapeless._
import shapeless.nat._

trait HasNested {
  sealed trait Nested[In] {
    type N <: Nat
    type Out
    def apply(in: In): Out
  }

  trait LowPri {
    type Aux[In, _N <: Nat, _Out] =
      Nested[In] {
        type N = _N
        type Out = _Out
      }

    implicit def zero[T]: Aux[T, _0, T] =
      new Nested[T] {
        type N = _0
        type Out = T
        def apply(in: T): Out = in
      }
  }

  object Nested
    extends LowPri {
    implicit def range[R <: Range]:
      Aux[
        R,
        _1,
        Seq[Int]
      ] =
      new Nested[R] {
        type N = _1
        type Out = Seq[Int]
        def apply(in: R): Out = in
      }

    implicit def cons[
      _N <: Nat,
      I[T] <: Seq[T],
      PrevIn,
      PrevOut
    ](
      implicit
      r: Lazy[Aux[PrevIn, _N, PrevOut]]
    ):
      Aux[
        I[PrevIn],
        Succ[_N],
        Seq[PrevOut]
      ] =
      new Nested[I[PrevIn]] {
        type N = Succ[_N]
        type Out = Seq[r.value.Out]
        def apply(in: I[PrevIn]): Out =
          in.map(r.value(_))
      }
  }
}
