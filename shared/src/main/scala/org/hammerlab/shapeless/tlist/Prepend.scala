package org.hammerlab.shapeless.tlist

trait Prepend[H, T <: TList] {
  def apply(h: H, t: T): H :: T
}
object Prepend {
  implicit def tnil[H, TL <: TNil]: Prepend[H, TL] =
    new Prepend[H, TL] {
      def apply(h: H, t: TL): H :: TL = ::(h, t)
    }

  implicit def cons[H, L <: TList.Aux[H]]: Prepend[H, L] =
    new Prepend[H, L] {
      def apply(h: H, l: L): H :: L = ::(h, l)
    }
}
