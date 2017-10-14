package org.hammerlab

import _root_.shapeless.HNil
import org.hammerlab.shapeless.hlist.{ HasFlattenedOps, HasSelectOps }

package object shapeless
  extends record.HasFindOps
    with hlist.HasFindOps
    with HasFlattenedOps
    with HasSelectOps {
  val ⊥ = HNil
  type ⊥ = HNil
}
