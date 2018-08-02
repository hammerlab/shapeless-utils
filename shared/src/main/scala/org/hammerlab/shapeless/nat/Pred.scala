package org.hammerlab.shapeless.nat

import shapeless._

/**
 * Inverse of [[Succ]]; type-level predecessor for each type-level integer
 *
 * May need implicit instances of type-level integers in scope for derivation to work:
 *
 * {{{
 * import hammerlab.shapeless._
 * }}}
 */
trait Pred[N <: Nat] {
  type P <: Nat
  def p: P
}

object Pred {
  implicit def fromSucc[_P <: Nat](implicit _p: _P): Pred.Aux[Succ[_P], _P] =
    new Pred[Succ[_P]] {
      type P = _P
      def p = _p
    }

  type Aux[N <: Nat, _P <: Nat] = Pred[N] { type P = _P }
}
