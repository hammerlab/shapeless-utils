package org.hammerlab.shapeless.hlist

import shapeless._
import shapeless.ops.hlist.Prepend

/**
 * Cartesian product of two [[HList]]s
 */
trait Cartesian[L <: HList, R <: HList] {
  type Out <: HList
  def apply(l: L, r: R): Out
}
trait LowPriCartesian {
  type Aux[L <: HList, R <: HList, _Out <: HList] = Cartesian[L, R] { type Out = _Out }
  def apply[L <: HList, R <: HList, _Out <: HList](fn: (L, R) ⇒ _Out): Aux[L, R, _Out] =
    new Cartesian[L, R] {
      type Out = _Out
      @inline def apply(l: L, r: R): Out = fn(l, r)
    }

  implicit def lhnil[R <: HList]: Aux[HNil, R, HNil] = apply { (_, _) ⇒ HNil }
}
object Cartesian
  extends LowPriCartesian {
  implicit def lcons[
       H,
       L <: HList,
       R <: HList,
    Prev <: HList,
     New <: HList
  ](
    implicit
    c: Aux[L, R, Prev],
    e: ElemwisePrepend.Aux[H, R, New],
    p: Prepend[New, Prev]
  ):
    Aux[
      H :: L,
      R,
      p.Out
    ] =
    apply {
      case (h :: l, r) ⇒
        p(
          e(h, r),
          c(l, r)
        )
    }
}
