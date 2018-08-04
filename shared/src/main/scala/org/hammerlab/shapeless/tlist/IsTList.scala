package org.hammerlab.shapeless.tlist

import hammerlab.shapeless.{ tlist ⇒ tl }
import shapeless.{ :: ⇒ _, _ }

/**
 * Type-class converting a type [[T]] to a corresponding [[TList]] of [[Elem elements]]
 */
trait IsTList[T, Elem] {
  type Out <: TList
  def apply(t: T): Out
}

trait LowPriIsTlist {
  type Aux[T, Elem, _O <: TList] = IsTList[T, Elem] { type Out = _O }

  /**
   * Constructor short-hand
   */
  def apply[T, Elem, _O <: TList](fn: T ⇒ _O): Aux[T, Elem, _O] =
    new IsTList[T, Elem] {
      type Out = _O
      def apply(t: T): Out = fn(t)
    }

  /**
   * Fallback: every [[T]] can be a [[TList]] of one [[T]]
   */
  implicit def singleton[T]: Aux[T, T, T :: TNil] = apply(t ⇒ tl.::(t, TNil))
}

object IsTList
  extends LowPriIsTlist {

  implicit def hnil[T]: Aux[HNil, T, TNil] =
    apply(_ ⇒ TNil)

  /**
   * A one-element [[HList]] is a [[TList]] of one [[T]]
   */
//  implicit def baseHList[T]: Aux[shapeless.::[T, HNil], T, T :: TNil] =
//    apply(
//      l ⇒ tl.::(l.head, TNil)
//    )

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
    tail: Lazy[Aux[L, T, TL]],
    pp: Prepend[T, TL]
  ):
    Aux[
      shapeless.::[T, L],
      T,
      T :: TL
    ] =
    apply(
      l ⇒
        tl.::(
          l.head,
          tail.value(l.tail)
        )
    )

  /**
   * If a [[Product]]'s [[Generic]] form is a [[TList]] (of [[Elem elements]]), then so is that [[Product]]
   *
   * Importantly: derives instances for tuples
   *
   * It will also derive instances for suitably-shaped case-classes (where all fields are the same type); unclear
   * whether that's a feature or a bug.
   *
   * @param g [[Generic]] evidence linking [[T]] to [[L]]
   * @param ev evidence that the generic form of [[T]], [[L]], is a [[TList]] of [[Elem]]s
   * @tparam T input / [[Product]] type
   * @tparam L corresponding generic / [[HList]] type
   * @tparam Elem underlying element-type comprising the [[TList]]
   * @return [[IsTList]] instance for [[Product]]-type [[T]]
   */
  implicit def product[
    T <: Product,
    L <: HList,
    Elem
  ](
    implicit
    g: Generic.Aux[T, L],
    ev: Lazy[IsTList[L, Elem]]
  ):
    Aux[T, Elem, ev.value.Out] =
    apply(
      t ⇒ ev.value(g.to(t))
    )
}
