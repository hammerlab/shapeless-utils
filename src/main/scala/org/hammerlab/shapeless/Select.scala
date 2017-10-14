package org.hammerlab.shapeless

import org.hammerlab.shapeless.Select.Ops
import shapeless._

/**
 * Port of [[shapeless.ops.hlist.Selector]] that supports selecting a field by querying for a super-type
 */
trait Select[L, +U] extends Serializable {
  def apply(l: L): U
}

object Select
  extends HasSelectOps {

  def apply[L, U](fn: L ⇒ U) = new Select[L, U] { override def apply(l: L) = fn(l) }

  implicit def cc[T, CC, L <: HList](implicit
                                     gen: Generic.Aux[CC, L],
                                     select: Select[L, T]): Select[CC, T] =
    Select[CC, T](cc ⇒ select(gen.to(cc)))

  implicit def select[H, T <: HList]: Select[H :: T, H] =
    Select[H :: T, H](_.head)

  implicit def recurse[H, L <: HList, U](implicit st : Select[L, U]): Select[H :: L, U] =
    Select[H :: L, U](l ⇒ st(l.tail))

  class Ops[T](val t: T) extends AnyVal {
    def iselect[U](implicit s: Select[T, U]): U = s(t)
  }
}

trait HasSelectOps {
  implicit def makeSelectOps[T](t: T): Ops[T] = new Ops(t)
}
