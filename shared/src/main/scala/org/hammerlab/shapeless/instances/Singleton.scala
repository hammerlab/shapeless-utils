package org.hammerlab.shapeless.instances

import shapeless._

/**
 * Summon the single element of type [[T]], if it exists (i.e. [[T]] is a case object / has [[Generic]] representation
 * [[HNil]])
 */
trait Singleton[T] {
  def apply(): T
}
object Singleton {
  implicit def singleton[T](implicit g: Generic.Aux[T, HNil]): Singleton[T] =
    new Singleton[T] {
      def apply(): T = g.from(HNil)
    }
}
