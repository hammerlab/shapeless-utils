package org.hammerlab.shapeless.instances

import cats.Show
import cats.syntax.show._
import shapeless.HList
import shapeless.ops.hlist.ToTraversable

import scala.reflect.ClassTag

trait InstanceMap[T] {
  def apply(): Map[String, T]
}
object InstanceMap {

  def apply[T]()(implicit i: InstanceMap[T]) = i()

  private def showAny[T]: Show[T] = new Show[T] { override def show(t: T): String = t.toString }

  implicit def instances[T, L <: HList](
    implicit
    i: Instances.Aux[T, L],
    ct: ClassTag[T],
    t: ToTraversable.Aux[L, List, T],
    show: Show[T] = showAny[T]
  ):
    InstanceMap[T] =
    new InstanceMap[T] {
      def apply(): Map[String, T] =
        i()
          .toList
          .map {
            t ⇒ t.show → t
          }
          .toMap
    }
}
