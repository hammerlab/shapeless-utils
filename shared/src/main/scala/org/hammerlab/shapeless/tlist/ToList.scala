package org.hammerlab.shapeless.tlist

import org.hammerlab.shapeless.tlist

trait ToList[T, TL] {
  def apply(tl: TL): List[T]
}
object ToList {
  implicit def tnil[T]: ToList[T, TNil] =
    new ToList[T, TNil] {
      def apply(tl: TNil): List[T] = Nil
    }

  implicit def cons[T, L <: TList](implicit ev: ToList[T, L]): ToList[T, T :: L] =
    new ToList[T, T :: L] {
      def apply(tl: T ::L): List[T] = tl.head :: ev(tl.tail)
    }

  implicit class Ops[L <: TList](val l: L) extends AnyVal {
    def toList[T](implicit tl: ToList[T, L]): List[T] = tl(l)
  }
}

trait HasToListOps {
  @inline implicit def ShapelessTListToListOps[L <: TList](l: L): ToList.Ops[L] = ToList.Ops(l)
}

trait HasToList
  extends HasToListOps {
  type ToList[T, TL] = tlist.ToList[T, TL]
   val ToList        = tlist.ToList
}
