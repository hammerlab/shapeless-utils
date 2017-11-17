package org.hammerlab.shapeless.coproduct;

import shapeless._

trait cast {
  /**
   * Type-class providing evidence that type [[T]] is structurally equivalent to an [[HList]]-type `L`
   *
   * Additionally provides a round-trip into and out of `L` for a given instance of [[T]]
   */
  trait Cast[T] {
    type L <: HList
    def iso(t: T): Iso[T, L]
    def apply(t: T)(fn: L ⇒ L): T = iso(t)(fn)
  }

  object Cast {

    type Aux[T, L0 <: HList] = Cast[T] { type L = L0 }

    implicit def cnil[L0 <: HList]: Aux[CNil, L0] =
      new Cast[CNil] {
        type L = L0
        override def iso(t: CNil) = ???
      }

    implicit def coproduct[H, T <: Coproduct, L0 <: HList](implicit
                                                           genH: Generic.Aux[H, L0],
                                                           lt: Aux[T, L0]): Aux[H :+: T, L0] =
      new Cast[H :+: T] {
        type L = L0
        override def iso(t: H :+: T) =
          t match {
            case Inl(h) ⇒
              new Iso[H :+: T, L](
                Inl(h),
                genH.to(h)
              ) {
                override def from(to: L) = Inl(genH.from(to))
              }
            case Inr(t) ⇒
              val iso = lt.iso(t)
              new Iso[H :+: T, L](
                Inr(t),
                iso.t
              ) {
                override def from(to: L) = Inr(iso.from(to))
              }
          }
      }

    implicit def sealedTrait[T, C <: Coproduct, L0 <: HList](implicit
                                                             genT: Generic.Aux[T, C],
                                                             l: Aux[C, L0]): Aux[T, L0] =
      new Cast[T] {
        type L = L0
        override def iso(t: T) = {
          val c = genT.to(t)
          val iso = l.iso(c)
          new Iso[T, L](t, iso.t) {
            override def from(to: L) = genT.from(iso.from(to))
          }
        }
      }

    implicit def caseClass[T, L0 <: HList](implicit genT: Generic.Aux[T, L0]): Aux[T, L0] =
      new Cast[T] {
        type L = L0
        override def iso(t: T) = Iso[T, L0](t)
      }

  }

  implicit class CastOps[T, L <: HList](t: T)(implicit c: Cast.Aux[T, L]) {
    def map(fn: L ⇒ L): T = c(t)(fn)
  }
}

object cast extends cast with Serializable
