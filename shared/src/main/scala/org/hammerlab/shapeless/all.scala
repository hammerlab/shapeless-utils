package org.hammerlab.shapeless

import org.hammerlab.shapeless.coproduct.{ cast, singleton }
import org.hammerlab.shapeless.hlist.{ HasFlattenedOps, HasSelectOps }
import org.hammerlab.shapeless.record.HasFieldOps
import shapeless.HNil

trait all
  extends record.HasFindOps
    with HasFieldOps
    with hlist.HasFindOps
    with HasFlattenedOps
    with HasSelectOps
    with cast
    with nats
    with singleton {
  val ⊥ = HNil
  type ⊥ = HNil
}
