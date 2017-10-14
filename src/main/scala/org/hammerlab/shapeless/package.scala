package org.hammerlab

import _root_.shapeless.HNil
import org.hammerlab.shapeless.hlist.{ HasFlattenedOps, HasSelectOps }
import org.hammerlab.shapeless.record.HasFindOps

package object shapeless
  extends HasFindOps
    with HasFlattenedOps
    with HasSelectOps {
  val ⊥ = HNil
  type ⊥ = HNil
}
