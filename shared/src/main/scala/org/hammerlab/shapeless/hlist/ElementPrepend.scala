package org.hammerlab.shapeless.hlist

import shapeless._

/**
 * Prepend an [[H element]] to [[T another]]; if the "tail" is already an [[HList]], the usual [[:: prepend]] is
 * performed; otherwise, a two-element [[HList]] is constructed from the two arguments
 */
trait ElementPrepend[H, T] {
  type Out <: HList
  def apply(h: H, t: T): Out
}
trait LowPriElementPrepend {
  type Aux[H, T, _O <: HList] = ElementPrepend[H, T] { type Out = _O }
  implicit def fallback[H, T]: Aux[H, T, H :: T:: HNil] =
    new ElementPrepend[H, T] {
      type Out = H :: T :: HNil
      def apply(h: H, t: T): Out = h :: t :: HNil
    }
}
object ElementPrepend
  extends LowPriElementPrepend {
  implicit def hlist[H, T <: HList]: Aux[H, T, H :: T] =
    new ElementPrepend[H, T] {
      type Out = H :: T
      def apply(h: H, t: T): Out = h :: t
    }
}
