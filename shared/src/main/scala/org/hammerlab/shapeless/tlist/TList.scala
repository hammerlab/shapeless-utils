package org.hammerlab.shapeless.tlist

import hammerlab.shapeless.{ tlist ⇒ tl }

sealed trait TList {
  type T

  // putting this here instead of in an Ops wrapper allows not explicitly specifying the type of the parameter to `fn`
  def map[
    Out,
    S >: this.type <: TList
  ](
    fn: T ⇒ Out
  )(
    implicit
    map: Map.Ax[S, T, Out]
  ) =
    map(this, fn)
}

object TList {
  type Aux[_T] = TList { type T = _T }
  implicit class Ops[L <: TList](val l: L) extends AnyVal {
    def ::[T](t: T)(implicit ev: Prepend[T, L]) = ev(t, l)
  }
  implicit def makeWithEvidence[T, Elem](t: T)(implicit ev: IsTList[T, Elem]): ev.Out = ev(t)
}

sealed trait TNil extends TList {
  type T = Nothing
  def ::[T](t: T) = tl.::(t, this)
}
case object TNil extends TNil

case class ::[_T, Tail <: TList](head: _T, tail: Tail) extends TList {
  type T = _T
  def ::[U >: this.type <: TList.Aux[T]](t: T): ::[T, U] = tl.::(t, this: U)
}
