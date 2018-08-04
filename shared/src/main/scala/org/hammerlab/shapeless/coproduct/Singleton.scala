package org.hammerlab.shapeless.coproduct

import org.hammerlab.shapeless.coproduct.Singleton.Ops
import shapeless._

/**
 * Type-class representing evidence that a type [[T]] wraps a single value of type [[V]].
 *
 * [[T]] may be a sealed hierarchy as long as all concrete implementations are case-classes with a single [[V]]
 * parameter.
 */
trait Singleton[T, V] {
  def apply(t: T)(fn: V ⇒ V): T
}

object Singleton {
  def apply[T, V](implicit l: Cast.Aux[T, V :: HNil]): Singleton[T, V] = fromLooksLike[T, V]

  implicit def fromLooksLike[T, V](implicit l: Cast.Aux[T, V :: HNil]): Singleton[T, V] =
    new Singleton[T, V] {
      override def apply(t: T)(fn: V ⇒ V) =
        l(t)(l ⇒ fn(l.head) :: HNil)
    }

  implicit class Ops[T, V](t: T)(implicit e: Singleton[T, V]) {
    def map(fn: V ⇒ V) = e(t)(fn)
  }
}

trait HasSingleton {
  import org.hammerlab.shapeless.coproduct
  type Singleton[T, V] = coproduct.Singleton[T, V]
   val Singleton       = coproduct.Singleton
  @inline implicit def ShapelessCoproductSingletonOps[T, V](t: T)(implicit e: Singleton[T, V]): Ops[T, V] = new Ops(t)
}
