package org.hammerlab.shapeless

import shapeless._
import shapeless.nat._

sealed trait Unroll[In, T[_]] {
  type N <: Nat
  type Out
}

trait LowPriUnroll {
  type Aux[In, T[_], _N <: Nat, _Out] =
    Unroll[In, T] {
      type N = _N
      type Out = _Out
    }

  type Natd[In, T[_], _N <: Nat] =
    Unroll[In, T] {
      type N = _N
    }

  implicit def zero[In, T[_]]: Aux[In, T, _0, In] =
    new Unroll[In, T] {
      type N = _0
      type Out = In
    }
}

object Unroll
  extends LowPriUnroll {
  implicit def rep[
    In,
    T[_],
    _N <: Nat,
    _Out
  ](
    implicit
    prev: Lazy[Aux[In, T, _N, _Out]]
  ):
    Aux[
      T[In],
      T,
      Succ[_N],
      _Out
    ] =
    new Unroll[T[In], T] {
      type N = Succ[_N]
      type Out = _Out
    }
}
