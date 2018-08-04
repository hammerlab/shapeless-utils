package org.hammerlab.shapeless.record

import org.hammerlab.shapeless.record.Find.Ops
import shapeless._
import shapeless.labelled.FieldType

/**
 * Type-class for recursively selecting a field [[K]] with value [[V]] in an object [[C]]
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
 * c.find[A]('a)       // A(123)
 * c.find[B]('b)       // B("abc")
 * c.find[Int]('n)     // 123
 * c.find[String]('s)  // "abc"
 *
 * c.find[Int]('x)     // doesn't compile
 * c.find[Int]('s)     // doesn't compile
 * }}}
 */
trait Find[C, K <: Symbol, V]
  extends Serializable {
  def apply(c: C): V
}

trait LowPriFind {
  def make[C, K <: Symbol, V0](fn: C ⇒ V0): Find[C, K, V0] =
    new Find[C, K, V0] {
      override def apply(c: C): V0 = fn(c)
    }

  /**
   * Extend a [[Find]] when an arbitrary element is prepended
   *
   * This needs to be lower-priority than [[Find.fromSelector]], because we prefer to recurse
   */
  implicit def continue[H, L <: HList, K <: Symbol, V](implicit findTail: Find[L, K, V]): Find[H :: L, K, V] =
    make(c ⇒ findTail(c.tail))
}

object Find
  extends LowPriFind {

  def apply[C, K <: Symbol, V](implicit find: Find[C, K, V]): Find[C, K, V] = find

  /** Recurse from a case-class to any of its fields, via its [[Generic]] */
  implicit def fromCC[CC, L <: HList, K <: Symbol, V](implicit
                                                      gen: LabelledGeneric.Aux[CC, L],
                                                      find: Lazy[Find[L, K, V]]): Find[CC, K, V] =
    make(cc ⇒ find.value(gen.to(cc)))

  implicit def consElem[K <: Symbol, V, L <: HList]: Find[FieldType[K, V] :: L, K, V] =
    make(_.head)

  /** Recurse from a case-class to any of its fields, via its [[Generic]] */
  implicit def fromCCRec[CC, L <: HList, K <: Symbol, V](implicit
                                                         gen: Generic.Aux[CC, L],
                                                         find: Lazy[Find[L, K, V]]): Find[CC, K, V] =
    make(cc ⇒ find.value(gen.to(cc)))

  /** Construct a [[Find]] by prepending an existing [[Find]] onto any [[HList]] */
  implicit def directCons[H, K <: Symbol, L <: HList, V](implicit
                                                         findHead: Lazy[Find[H, K, V]]): Find[H :: L, K, V] =
    make(c ⇒ findHead.value(c.head))

  class Ops[T](val t: T) {
    def find[V](w: Witness.Lt[Symbol])(implicit f: Find[T, w.T, V]): V = f(t)
  }
}

trait HasFind {
  import org.hammerlab.shapeless.{ record ⇒ r }
  type Find[C, K <: Symbol, V] = r.Find[C, K, V]
   val Find                    = r.Find
  @inline implicit def ShapelessRecordFindOps[T](t: T): Ops[T] = new Ops(t)
}
