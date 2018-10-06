package org.hammerlab.shapeless.instances

import hammerlab.either._

import shapeless.HList
import shapeless.ops.hlist.ToTraversable

import scala.reflect.ClassTag

trait InstanceMap[T] {
  def apply(): Map[String, String | T]
}
object InstanceMap {

  def apply[T]()(implicit i: InstanceMap[T]) = i()

  implicit def instances[T, L <: HList](
    implicit
    i: Instances.Aux[T, L],
    ct: ClassTag[T],
    t: ToTraversable.Aux[L, List, T]
  ):
    InstanceMap[T] =
    new InstanceMap[T] {
      def apply(): Map[String, String | T] =
        i()
          .toList
          .map {
            o ⇒
              o.toString →
                Right(o)
          }
          .toMap
          .withDefault {
            k ⇒
              Left(
                s"Unrecognized ${ct.runtimeClass.getName}: $k"
              )
          }
    }
}
