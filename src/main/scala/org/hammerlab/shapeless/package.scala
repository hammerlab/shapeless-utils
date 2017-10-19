package org.hammerlab

import _root_.shapeless.HNil
import org.hammerlab.shapeless.hlist.{ HasFlattenedOps, HasSelectOps }
import org.hammerlab.shapeless.record.HasFieldOps

package object shapeless
  extends record.HasFindOps
    with HasFieldOps
    with hlist.HasFindOps
    with HasFlattenedOps
    with HasSelectOps {
  val ⊥ = HNil
  type ⊥ = HNil
}
