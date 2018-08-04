package org.hammerlab.shapeless.tlist

import hammerlab.shapeless.{ tlist ⇒ tl }
import shapeless.{ :: ⇒ _, _ }

/**
 * Type-class converting a type [[T]] to a corresponding [[TList]]
 */
trait IsTList[T] {
  type Out <: TList
  def apply(t: T): Out
}

trait LowPriIsTlist {
  type Aux[T, _O <: TList] =
    IsTList[T] {
      type Out = _O
    }

  /**
   * Constructor short-hand
   */
  def apply[T, _O <: TList](fn: T ⇒ _O): Aux[T, _O] =
    new IsTList[T] {
      type Out = _O
      def apply(t: T): Out = fn(t)
    }

  /** Every [[TList]] is already a [[TList]] */
  implicit def id[T <: TList]: Aux[T, T] = apply(t ⇒ t)
}

object IsTList
  extends LowPriIsTlist {

  implicit val hnil: Aux[HNil, TNil] =
    apply(_ ⇒ TNil)

  /**
   * Prepend an element to a [[TList]]-able [[HList]]
   *
   * @param tail turn an [[L]] into a [[TList]] of [[T]]s
   * @tparam T element-type
   * @tparam L [[HList]]-type made of [[T]]s
   * @return [[IsTList]] instance for prepending a [[T]] to a [[TList]]
   */
  implicit def consHList[
     T,
     L <: HList,
    TL <: TList
  ](
    implicit
    tail: Lazy[Aux[L, TL]],
    pp: Prepend[T, TL]
  ):
    Aux[
      shapeless.::[T, L],
      T :: TL
    ] =
    apply(
      l ⇒
        l.head ::
        tail.value(l.tail)
    )

  /**
   * If a [[Product]]'s [[Generic]] form is a [[TList]], then so is that [[Product]]
   *
   * Importantly: derives instances for tuples
   *
   * It will also derive instances for suitably-shaped case-classes (where all fields are the same type); unclear
   * whether that's a feature or a bug.
   *
   * @param g [[Generic]] evidence linking [[T]] to [[L]]
   * @param ev evidence that the generic form of [[T]], [[L]], is a [[TList]]
   * @tparam T input / [[Product]] type
   * @tparam L corresponding generic / [[HList]] type
   * @return [[IsTList]] instance for [[Product]]-type [[T]]
   */
  implicit def product[
     T,
     L <: HList,
    TL <: TList
  ](
    implicit
    g: Generic.Aux[T, L],
    ev: Lazy[IsTList.Aux[L, TL]]
  ):
    Aux[
      T,
      TL
    ] =
    apply(
      t ⇒ ev.value(g.to(t))
    )
}
