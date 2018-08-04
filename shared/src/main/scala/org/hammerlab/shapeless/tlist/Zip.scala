package org.hammerlab.shapeless.tlist

trait Zip[L <: TList, R <: TList] {
  type Out <: TList
  def apply(l: L, r: R): Out
}
object Zip {
  type Aux[L <: TList, R <: TList, _O <: TList] = Zip[L, R] { type Out = _O }

  implicit val tnil: Aux[TNil, TNil, TNil] =
    new Zip[TNil, TNil] {
      type Out = TNil
      override def apply(l: TNil, r: TNil): TNil = l
    }

  implicit def cons[
    EL,
    ER,
    L <: TList,
    R <: TList
  ](
    implicit
    ev: Zip[L, R]
  ):
    Aux[
      EL :: L,
      ER :: R,
      (EL, ER) :: ev.Out
    ] =
    new Zip[EL :: L, ER :: R] {
      type Out = (EL, ER) :: ev.Out
      def apply(
        l: EL :: L,
        r: ER :: R
      ):
        (EL, ER) :: ev.Out =
        ::(
          (l.head, r.head),
          ev(l.tail, r.tail)
        )
    }

  implicit class Ops[L <: TList](val l: L) extends AnyVal {
    def zip[R <: TList](r: R)(implicit z: Zip[L, R]): z.Out = z(l, r)
  }
}
