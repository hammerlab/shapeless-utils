package org.hammerlab.shapeless

import org.hammerlab.shapeless.Flattened.Ops
import shapeless._
import shapeless.ops.hlist.Prepend

trait Flattened[L]
  extends Serializable {
  type F <: HList
  def apply(l: L): F
}

trait LowestPri {
  type Aux[L, F0] = Flattened[L] { type F = F0 }

  def make[L, F0 <: HList](fn: L ⇒ F0): Aux[L, F0] =
    new Flattened[L] {
      type F = F0
      override def apply(l: L) = fn(l)
    }

  // Put anything with HNil, if nothing else matches
  implicit def directSingle[H]: Aux[H, H :: HNil] = make(_ :: HNil)
}

trait LowPriFlattenedImplicits extends LowestPri {
  // prepend an element directly if it can't be flattened further (via higher-priority implicits below)
  implicit def directCons[H, T <: HList, FT <: HList](implicit flatT: Aux[T, FT]): Aux[H :: T, H :: FT] =
    make(
      l ⇒
        l.head ::
          flatT(l.tail)
    )
}

object Flattened
  extends LowPriFlattenedImplicits
    with HasFlattenedOps {

  def apply[L](implicit flat: Flattened[L]): Aux[L, flat.F] = flat

  implicit val hnil: Aux[HNil, HNil] = make(l ⇒ l)

  // Flatten and prepend an HList
  implicit def nestedCons[H <: HList, FH <: HList, T <: HList, FT <: HList, Out <: HList](
      implicit
      flatH: Aux[H, FH],
      flatT: Aux[T, FT],
      concat: Prepend.Aux[FH, FT, Out]
  ): Aux[H :: T, Out] =
    make(
      l ⇒
        concat(
          flatH(l.head),
          flatT(l.tail)
        )
    )

  // Flatten and prepend a Product (e.g. case-class)
  implicit def nestedCCCons[H <: Product, HL <: HList, FH <: HList, T <: HList, FT <: HList, Out <: HList](
      implicit
      gen: Generic.Aux[H, HL],
      flatH: Aux[HL, FH],
      flatT: Aux[T, FT],
      concat: Prepend.Aux[FH, FT, Out]
  ): Aux[H :: T, Out] =
    make(
      l ⇒
        concat(
          flatH(gen.to(l.head)),
          flatT(l.tail)
        )
    )

  // Flatten a case-class directly
  implicit def cc[CC <: Product, L <: HList, FL <: HList](
      implicit
      gen: Generic.Aux[CC, L],
      flat: Aux[L, FL]
  ): Aux[CC, FL] =
    make(
      cc ⇒
        flat(
          gen.to(cc)
        )
    )

  class Ops[T](val t: T) extends AnyVal {
    def flatten(implicit f: Flattened[T]): f.F = f(t)
  }
}

trait HasFlattenedOps {
  implicit def makeFlattenedOps[T](t: T): Ops[T] = new Ops(t)
}
