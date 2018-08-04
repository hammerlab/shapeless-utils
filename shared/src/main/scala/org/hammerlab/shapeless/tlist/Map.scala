package org.hammerlab.shapeless.tlist

trait Map[InList <: TList, Out] {
  type OutList <: TList
  type In
  def apply(in: InList, fn: In ⇒ Out): OutList
}

trait LowPriMap {
  type Aux[InList <: TList, _In, Out, _OutList <: TList] =
    Map[InList, Out] {
      type In = _In
      type OutList = _OutList
    }

  implicit def tnilFlex[InList <: TNil, _In, Out]: Aux[InList, _In, Out, TNil] =
    new Map[InList, Out] {
      type In = _In
      type OutList = TNil
      override def apply(in: InList, fn: In ⇒ Out): TNil = TNil
    }
}
object Map
  extends LowPriMap {

  type Ax[InList <: TList, _In, Out] =
    Map[InList, Out] {
      type In = _In
    }

  implicit def tnilSpecialCase[InList <: TNil, Out]: Aux[InList, Out, Out, TNil] =
    new Map[InList, Out] {
      type In = Out
      type OutList = TNil
      override def apply(in: InList, fn: In ⇒ Out): TNil = TNil
    }

  implicit def cons[
     InList <: TList,
    _In,
     Out,
    _OutList <: TList
  ](
    implicit
    ev: Aux[InList, _In, Out, _OutList]
  ):
    Aux[
      _In :: InList,
      _In,
      Out,
      Out :: _OutList
    ] =
    new Map[_In :: InList, Out] {
      type In = _In
      type OutList = Out :: _OutList
      def apply(in: _In :: InList, fn: In ⇒ Out): OutList =
        in match {
          case h :: t ⇒
            ::(fn(h), ev(t, fn))
        }
    }
}
