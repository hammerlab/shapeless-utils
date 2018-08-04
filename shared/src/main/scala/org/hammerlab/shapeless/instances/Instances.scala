package org.hammerlab.shapeless.instances

import hammerlab.shapeless.hlist.Cartesian
import shapeless._
import shapeless.ops.hlist.Prepend

trait Instances[In] {
  type Out <: HList
  def apply(): Out
}
trait LowPriInstances {
  type Aux[In, _O <: HList] = Instances[In] { type Out = _O }
  def apply[In, _O <: HList](out: _O): Aux[In, _O] =
    new Instances[In] {
      type Out = _O
      def apply(): Out = out
    }
  implicit def product[
    T,
    L <: HList
  ](
    implicit
    g: Generic.Aux[T, L],
    i: Lazy[Instances[L]]
  ):
    Aux[
      T,
      i.value.Out
    ] =
    apply(
      i.value()
    )

  implicit def cons[
    H,
    L <: HList,
    HI <: HList,
    LI <: HList
  ](
    implicit
    hi: Aux[H, HI],
    li: Aux[L, LI],
    c: Cartesian[HI, LI]
  ):
    Aux[H :: L, c.Out] =
    apply(
      c(
        hi(),
        li()
      )
    )
}
object Instances
  extends LowPriInstances {

  implicit def singleton[T](implicit s: Singleton[T]): Aux[T, T :: HNil] = apply(s() :: HNil)

  implicit val cnil: Aux[CNil, HNil] = apply(HNil)
  implicit val hnil: Aux[HNil, HNil] = apply(HNil)

  implicit def one[T](
    implicit
    i: Instances[T]
  ):
    Aux[
      T :: HNil,
      i.Out
    ] =
    apply(
      i()
    )

  implicit def ccons[
    H,
    C <: Coproduct,
    HI <: HList,
    CI <: HList
  ](
    implicit
    hi: Lazy[Aux[H, HI]],
    ci: Lazy[Aux[C, CI]],
    pp: Prepend[HI, CI]
  ):
    Aux[
      H :+: C,
      pp.Out
    ] =
    apply(
      pp(
        hi.value(),
        ci.value()
      )
    )

  implicit def coproduct[
    T,
    C <: Coproduct
  ](
    implicit
    g: Generic.Aux[T, C],
    i: Lazy[Instances[C]]
  ):
    Aux[
      T,
      i.value.Out
    ] =
    apply(
      i.value()
    )
}
