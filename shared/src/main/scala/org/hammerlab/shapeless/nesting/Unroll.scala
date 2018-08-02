package org.hammerlab.shapeless.nesting

import shapeless._
import shapeless.nat._

trait HasUnroll {
  /**
   * Type-class that reduces an [[In input]] type to a series of nested applications of a type-constructur [[T]]
   *
   * "Returns" the number `N` of levels that were "unrolled", and the remaining / irreducible type `Out`
   */
  sealed trait Unroll[In, T[_]] {
    type N <: Nat
    type Out
  }

  trait LowPriUnroll {
    /**
     * Alias for expressing all input- and output-types
     *
     * @tparam In input type to reduce
     * @tparam T type-constructor to iteratively reduce [[In]] by
     * @tparam _N number of levels of [[T]] that can be extracted from [[In]]
     * @tparam _Out the remaining type after [[_N]] levels of [[T]] have been extracted from [[In]]
     */
    type Aux[
      In,
      T[_],
      _N <: Nat,
      _Out
    ] =
      Unroll[In, T] {
        type N = _N
        type Out = _Out
      }

    /**
     * Base case: every type is equivalent to a type-constructor applied to itself [[_0 0]] times
     *
     * @tparam In input- (and output-) type
     * @tparam T type constructor
     */
    implicit def zero[In, T[_]]: Aux[In, T, _0, In] =
      new Unroll[In, T] {
        type N = _0
        type Out = In
      }
  }

  object Unroll
    extends LowPriUnroll {

    /**
     * Inductive step: if a type [[In]] is [[_N N]] applications of [[T]] on top of [[_Out]], then [[T]] applied to [[In]]
     * is [[_N N]]+1 layers of [[T]] above [[_Out]]
     *
     * @param prev evidence that [[In]] is [[_N]] applications of [[T]] on top of [[_Out]]
     * @tparam In previous input-type
     * @tparam T type-constructor being repeatedly unrolled
     * @tparam _N previous nesting level
     * @tparam _Out previous "remainder" type
     * @return evidence that [[T]] applied to [[In]] is one more layer of [[T]] above [[_Out]]
     */
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
}
