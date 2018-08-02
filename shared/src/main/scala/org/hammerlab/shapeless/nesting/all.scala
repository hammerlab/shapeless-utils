package org.hammerlab.shapeless.nesting

import org.hammerlab.shapeless.nesting

trait all
  extends seq.all
     with HasUnroll {

  object seq extends nesting.seq.all
}
