package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.hlist.Find.Ops
import shapeless._

trait Find[C, F] extends Serializable {
  def apply(c: C): F
}

trait LowestPriFind {
  def apply[C, F](implicit find: Find[C, F]): Find[C, F] = find

  def make[C, F](fn: C ⇒ F): Find[C, F] =
    new Find[C, F] {
      override def apply(c: C) = fn(c)
    }

  implicit def cc[CC, L <: HList, F](implicit
                                     gen: Generic.Aux[CC, L],
                                     find: Lazy[Find[L, F]]
                                    ): Find[CC, F] =
    make(cc ⇒ find.value(gen.to(cc)))

  /** Low-priority: [[K]] exists in [[::.tail]]; prepend arbitrary element "for free". */
  implicit def cons[H, T <: HList, K](implicit find: Lazy[Find[T, K]]): Find[H :: T, K] = make(l ⇒ find.value(l.tail))
}

trait LowPriFind extends LowestPriFind {
  /** Medium-priority: [[K]] can be found within [[::.head]] [[H]]; find it there instead of within [[::.tail]] [[T]] */
  implicit def headRecurse[H, T <: HList, K](implicit find: Lazy[Find[H, K]]): Find[H :: T, K] =
    make(l ⇒ find.value(l.head))
}

object Find extends LowPriFind {
  /** Highest-priority: [[::.head]] is the type we seek; return it */
  implicit def foundHead[H, T <: HList]: Find[H :: T, H] = make(_.head)

  class Ops[T](val t: T) extends AnyVal {
    def findt[K](implicit f: Find[T, K]): K = f(t)
  }
}

trait HasFindOps {
  implicit def makeHListFindOps[T](t: T): Ops[T] = new Ops(t)
}
