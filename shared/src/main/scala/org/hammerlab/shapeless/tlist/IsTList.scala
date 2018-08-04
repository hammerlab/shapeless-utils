package org.hammerlab.shapeless.tlist

import hammerlab.shapeless.{ tlist ⇒ tl }
import shapeless._

/**
 * Type-class converting a type [[T]] to a corresponding [[TList]] of [[Elem elements]]
 */
trait IsTList[T, Elem] {
  def apply(t: T): TList.Aux[Elem]
}

trait LowPriIsTlist {
  /**
   * Constructor short-hand
   */
  def apply[T, Elem, N <: Nat](fn: T ⇒ TList.Aux[Elem]): IsTList[T, Elem] =
    new IsTList[T, Elem] {
      def apply(t: T): TList.Aux[Elem] = fn(t)
    }

  /**
   * Fallback: every [[T]] can be a [[TList]] of one [[T]]
   */
  implicit def singleton[T]: IsTList[T, T] = apply(t ⇒ tl.::(t, TNil))
}

object IsTList
  extends LowPriIsTlist {

  /**
   * A one-element [[HList]] is a [[TList]] of one [[T]]
   */
  implicit def baseHList[T]: IsTList[shapeless.::[T, HNil], T] =
    apply(
      l ⇒ tl.::(l.head, TNil)
    )

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
    L <: HList
  ](
    implicit
    tail: Lazy[IsTList[L, T]]
  ):
    IsTList[
      shapeless.::[T, L],
      T
    ] =
    apply(
      l ⇒
        tl.::(
          l.head,
          tail.value(l.tail)
        )
    )

  /**
   * If a [[Product]]'s [[Generic]] form is a [[TList]] (of [[N]] [[Elem elements]]), then so is that [[Product]]
   *
   * Importantly: derives instances for tuples
   *
   * It will also derive instances for suitably-shaped case-classes (where all fields are the same type); unclear
   * whether that's a feature or a bug.
   *
   * @param g [[Generic]] evidence linking [[T]] to [[L]]
   * @param ev evidence that the generic form of [[T]], [[L]], is a [[TList]] of [[N]] [[Elem]]s
   * @tparam T input / [[Product]] type
   * @tparam L corresponding generic / [[HList]] type
   * @tparam Elem underlying element-type comprising the [[TList]]
   * @tparam N number of [[Elem]]s in the [[TList]] / [[T]] / [[L]]
   * @return [[IsTList]] instance for [[Product]]-type [[T]]
   */
  implicit def product[
    T <: Product,
    L <: HList,
    Elem,
    N <: Nat
  ](
    implicit
    g: Generic.Aux[T, L],
    ev: IsTList[L, Elem]
  ):
    IsTList[T, Elem] =
    apply(
      t ⇒ ev(g.to(t))
    )
}
