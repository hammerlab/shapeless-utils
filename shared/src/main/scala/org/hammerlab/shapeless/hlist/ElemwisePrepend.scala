package org.hammerlab.shapeless.hlist

import shapeless._

/**
 * Prepend an [[E element]] to each element in an [[L HList]]
 */
trait ElemwisePrepend[E, L <: HList] {
  type Out <: HList
  def apply(e: E, l: L): Out
}
object ElemwisePrepend {
  type Aux[E, L <: HList, _O] = ElemwisePrepend[E, L] { type Out = _O }
  implicit def hnil[T]: Aux[T, HNil, HNil] =
    new ElemwisePrepend[T, HNil] {
      type Out = HNil
      def apply(e: T, l: HNil): Out = HNil
    }

  implicit def cons[
  E,
  H,
  L <: HList
  ](
       implicit
       ep: ElemwisePrepend[E, L],
       el: ElementPrepend[E, H]
   ):
  Aux[
    E,
    H :: L,
    el.Out :: ep.Out
    ] =
    new ElemwisePrepend[E, H :: L] {
      type Out = el.Out :: ep.Out
      def apply(e: E, l: H :: L): Out =
        l match {
          case h :: t ⇒
            el(e, h) :: ep(e, t)
        }
    }
}
