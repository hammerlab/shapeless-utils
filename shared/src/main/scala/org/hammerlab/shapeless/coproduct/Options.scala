package org.hammerlab.shapeless.coproduct

import shapeless._

/**
 * Compute an [[HList]] comprised of all the options in a [[Coproduct]]
 */
trait Options[In] {
  type Out <: HList
}
object Options {
  type Aux[In, _O <: HList] = Options[In] { type Out = _O }
  implicit val nil: Aux[CNil, HNil] =
    new Options[CNil] {
      type Out = HNil
    }

  implicit def cons[H, C <: Coproduct](implicit o: Options[C]): Aux[H :+: C, H :: o.Out] =
    new Options[H :+: C] {
      type Out = H :: o.Out
    }

  implicit def coproduct[T, C <: Coproduct](implicit g: Generic.Aux[T, C], o: Options[C]): Aux[T, o.Out] =
    new Options[T] {
      type Out = o.Out
    }
}

trait HasOptions {
  import org.hammerlab.shapeless.coproduct
  type Options[In] = coproduct.Options[In]
   val Options     = coproduct.Options
}
