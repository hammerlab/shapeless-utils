package org.hammerlab.shapeless

import shapeless.HNil

trait all
  extends record.HasFindOps
     with coproduct.cast
     with coproduct.singleton
     with hlist.HasFindOps
     with hlist.HasFlattenedOps
     with hlist.HasSelectOps
     with nat.implicits
     with record.HasFieldOps {
   val ⊥ = HNil
  type ⊥ = HNil
}
