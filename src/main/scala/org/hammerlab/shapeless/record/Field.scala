package org.hammerlab.shapeless.record

import org.hammerlab.shapeless.record.Field.Ops
import shapeless._
import shapeless.ops.record.Selector

/**
 * Type-class for recursively selecting a field [[K]] in an object [[C]]
 *
 * Example:
 *
 * {{{
 * case class A(n: Int)
 * case class B(s: String)
 * case class C(a: A, b: B)
 *
 * val c = C(A(123), B("abc"))
 *
 * c.field('a)  // A(123)
 * c.field('b)  // B("abc")
 * c.field('n)  // 123
 * c.field('s)  // "abc"
 * c.field('x)  // doesn't compile
 * }}}
 */
trait Field[C, K]
  extends Serializable {
  type V
  def apply(c: C): V
}

trait LowPriField {
  type Aux[C, K, V0] = Field[C, K] { type V = V0 }

  def make[C, K, V0](fn: C ⇒ V0): Aux[C, K, V0] =
    new Field[C, K] {
      type V = V0
      override def apply(c: C): V0 = fn(c)
    }

  /**
   * Extend a [[Field]] when an arbitrary element is prepended
   *
   * This needs to be lower-priority than [[Field.fromSelector]], because we prefer to recurse
   */
  implicit def continue[H, L <: HList, K](implicit findTail: Field[L, K]): Aux[H :: L, K, findTail.V] =
    make(c ⇒ findTail(c.tail))
}

object Field
  extends LowPriField {

  def apply[C, K](implicit find: Field[C, K]): Aux[C, K, find.V] = find

  def Aux[C, K, V](implicit find: Field.Aux[C, K, V]): Aux[C, K, V] = find

  /** Bridge a case-class to its [[LabelledGeneric]] */
  implicit def fromCC[CC, L <: HList, K](implicit
                                         gen: LabelledGeneric.Aux[CC, L],
                                         find: Lazy[Field[L, K]]): Aux[CC, K, find.value.V] =
    make(cc ⇒ find.value(gen.to(cc)))

  /** Convert a [[Selector]] to a [[Field]] */
  implicit def fromSelector[C <: HList, K](implicit sl: Selector[C, K]): Aux[C, K, sl.Out] =
    make(sl(_))

  implicit def fromCCRec[CC, L <: HList, K](implicit
                                            gen: Generic.Aux[CC, L],
                                            find: Lazy[Field[L, K]]
                                           ): Aux[CC, K, find.value.V] =
    make(cc ⇒ find.value(gen.to(cc)))

  /** Construct a [[Field]] by prepending an existing [[Field]] onto any [[HList]] */
  implicit def directCons[H, K, L <: HList](implicit findHead: Lazy[Field[H, K]]): Aux[H :: L, K, findHead.value.V] =
    make(c ⇒ findHead.value(c.head))

  class Ops[T](val t: T) extends AnyVal {
    def field(w: Witness)(implicit f: Field[T, w.T]): f.V = f(t)
  }
}

trait HasFieldOps {
  implicit def makeRecordFieldOps[T](t: T): Ops[T] = new Ops(t)
}
