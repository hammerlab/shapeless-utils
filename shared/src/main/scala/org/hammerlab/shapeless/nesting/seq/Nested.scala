package org.hammerlab.shapeless.nesting.seq

import shapeless._
import shapeless.nat._

/**
 * Type-class for counting and homogenizing nested types comprised of nested [[Seq]]-applications
 *
 * Output types:
 *
 * - `N`: number of levels of [[Seq]] (or its implementations) that comprise [[In]]
 * - `Out`: `N` levels of vanilla [[Seq]] applied to the same underlying type as [[In]]
 *
 * Includes an `apply` method for converting an instence of [[In]] to an `Out`
 *
 * @tparam In input-type
 */
sealed trait Nested[In] {
  type N <: Nat
  type Out
  @inline def apply(in: In): Out
}

trait LowPri {
  type Aux[In, _N <: Nat, _Out] =
    Nested[In] {
      type N = _N
      type Out = _Out
    }

  /**
   * Base case: [[_0 0]] levels of [[Seq]] to unroll/homogenize/re-roll
   */
  implicit def zero[T]: Aux[T, _0, T] =
    new Nested[T] {
      type N = _0
      type Out = T
      def apply(in: T): Out = in
    }
}

object Nested
  extends LowPri {
  /**
   * [[Range]]s get special-cased since they mask one level of [[Seq]]-wrapping, and aren't picked up by [[cons]]
   * below
   */
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

  /**
   * Inductive step: if we can convert a [[PrevIn]] to a [[PrevOut]], the latter being [[Seq]] (or one of its
   * subtypes) applied [[_N]] times, then [[I]] (a [[Seq]]-subtype) applied to [[PrevIn]] can be converted to [[_N]]+1
   * applications of [[Seq]]
   *
   * @param r converter from [[PrevIn]] to [[PrevOut]]; homogenizes [[_N]] levels of [[Seq]]-application
   * @tparam _N previous number of levels of [[Seq]]-application
   * @tparam I a [[Seq]] sub-type; will be coerced to [[Seq]] itself on the output type of the returned [[Nested]]
   *           instance
   * @tparam PrevIn previous input type; [[_N]] levels of application of [[Seq]]-subtypes
   * @tparam PrevOut previous output type; [[_N]] levels of application of [[Seq]]
   * @return a [[Nested]] instance that can convert [[I]]⊙[[PrevIn]] to [[Seq]]⊙[[PrevOut]], additionally recording
   *         that this represents [[_N]]+1 levels of conversion
   */
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
