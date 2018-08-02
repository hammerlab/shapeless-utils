package org.hammerlab.shapeless.tlist

import shapeless._
import shapeless.nat._

/**
 * A [[TList]] ("typed list") is a list with elements of a given type [[T]], whose length is the type-level integer
 * [[N]].
 *
 * For example, a 3-tuple of [[Int]]s can be converted to a [[TList]] of length [[_3 3]], but a tuple with elements of
 * different types cannot.
 */
sealed trait TList[T, N <: Nat] {
  def head: T
}

object TList {

  /**
   * Any object can be a [[_1 1]]-element [[TList]] of its own type
   */
  case class Base[T](head: T) extends TList[T, _1]

  /**
   * Prepending a [[T same-typed element]] to an existing [[TList]] yields a new [[TList]] whose length is one larger
   */
  case class Cons[T, P <: Nat](head: T, tail: TList[T, P]) extends TList[T, Succ[P]]

  /**
   * Convert an instance of [[T]] to a [[TList]] of [[N]] [[Elem elements]], provided an implicit implementation in the
   * form of a suitable [[IsTList]] instance.
   */
  implicit def wrap[
    T,
    Elem,
    N <: Nat
  ](
    t: T
  )(
    implicit
    ev: IsTList[T, Elem, N]
  ):
    TList[Elem, N] =
    ev(t)
}
