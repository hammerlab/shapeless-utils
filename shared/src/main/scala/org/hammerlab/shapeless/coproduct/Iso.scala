package org.hammerlab.shapeless.coproduct

import shapeless.Generic

abstract class Iso[From, To](f: From, val t: To) {
  def from(to: To): From
  def apply(fn: To ⇒ To): From =
    from(
      fn(
        t
      )
    )
}

object Iso {
  implicit def apply[T, L](t: T)(implicit gen: Generic.Aux[T, L]): Iso[T, L] =
    new Iso[T, L](t, gen.to(t)) {
      override def from(to: L) = gen.from(to)
    }
}
