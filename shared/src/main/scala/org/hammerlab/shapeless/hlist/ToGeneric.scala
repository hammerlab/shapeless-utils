package org.hammerlab.shapeless.hlist

import shapeless._

/**
 * The [[Generic.to `to`]] half of a [[Generic]]
 *
 * Instances can be bootstrapped from full [[Generic]]s, but direct, manual definition of [[ToGeneric]] can also be
 * useful in cases where the [[Generic.from `from`]] half is not well-defined (e.g. where if constructor is restricted /
 * overloaded)
 */
trait ToGeneric[In] {
  type Out
  def apply(in: In): Out
}

object ToGeneric
  extends Serializable {
  type Aux[In, Out0] = ToGeneric[In] { type Out = Out0 }
  def aux[I, O](fn: I ⇒ O): Aux[I, O] =
    new ToGeneric[I] {
      type Out = O
      def apply(i: I): O = fn(i)
    }

  implicit def generic[I, O](implicit g: Generic.Aux[I, O]): Aux[I, O] = aux(g.to)
}
