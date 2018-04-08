package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.hlist.Flatten.Ops
import shapeless._
import shapeless.ops.hlist.Prepend

/**
 * Type-class that computes a recursively-flattened [[HList]] `F` for an input type [[In]].
 *
 * Case-classes and [[HList]]s are expanded.
 */
trait Flatten[In]
  extends Serializable {
  type Out <: HList
  def apply(l: In): Out
}

trait LowestPri {
  type Aux[In, Out0] = Flatten[In] { type Out = Out0 }

  def make[In, Out0 <: HList](fn: In ⇒ Out0): Aux[In, Out0] =
    new Flatten[In] {
      type Out = Out0
      override def apply(l: In) = fn(l)
    }
}

trait LowPriFlattenedImplicits extends LowestPri {
  // prepend an element directly if it can't be flattened further (via higher-priority implicits below)
  implicit def directCons[
     H,
     T <: HList,
    FT <: HList
  ](
    implicit
    ft: Lazy[Aux[T, FT]]
  ):
    Aux[
      H ::  T,
      H :: FT
    ] =
    make {
      case h :: t ⇒
           h :: ft.value(t)
    }
}

object Flatten
  extends LowPriFlattenedImplicits
    with HasFlattenedOps {

  def apply[In](implicit flat: Flatten[In]): Aux[In, flat.Out] = flat

  implicit val hnil: Aux[HNil, HNil] = make(l ⇒ l)

  // Flatten and prepend a Product (e.g. case-class)
  implicit def nestedCCCons[
      H <: Product,
     FH <: HList,
      T <: HList,
     FT <: HList,
    Out <: HList
  ](
    implicit
    fh : Lazy[Aux[H, FH]],
    ft : Lazy[Aux[T, FT]],
    ++ : Prepend.Aux[FH, FT, Out]
  ): Aux[H :: T, Out] =
    make {
      case h :: t ⇒
        ++(
          fh.value(h),
          ft.value(t)
        )
    }

  // Flatten a case-class directly
  implicit def cc[
    CC <: Product,
     L <: HList,
    FL <: HList
  ](
    implicit
    gen: Generic.Aux[CC, L],
    flat: Lazy[Aux[L, FL]]
  ): Aux[CC, FL] =
    make(
      cc ⇒
        flat.value(
          gen.to(cc)
        )
    )

  class Ops[T](val t: T) extends AnyVal {
    def flatten(implicit f: Flatten[T]): f.Out = f(t)
  }
}

trait HasFlattenedOps {
  implicit def makeFlattenedOps[T](t: T): Ops[T] = new Ops(t)
}
