package org.hammerlab.shapeless

import org.hammerlab.{ shapeless ⇒ ohs }
import shapeless.HNil

trait all
  extends coproduct.all
     with     hlist.all
     with instances.all
     with       nat.all
     with   nesting.all
     with    record.all {

   val ⊥ = HNil
  type ⊥ = HNil

  object coproduct extends ohs.coproduct.all
  object     hlist extends ohs.    hlist.all
  object instances extends ohs.instances.all
  object       nat extends ohs.      nat.all
  object   nesting extends ohs.  nesting.all
  object    record extends ohs.   record.all
  object     tlist extends ohs.    tlist.all
}
