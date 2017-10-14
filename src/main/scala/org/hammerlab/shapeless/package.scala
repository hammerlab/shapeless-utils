package org.hammerlab

import _root_.shapeless.HNil

package object shapeless
  extends HasFindOps
    with HasFlattenedOps
    with HasSelectOps {
  val ⊥ = HNil
  type ⊥ = HNil
}
