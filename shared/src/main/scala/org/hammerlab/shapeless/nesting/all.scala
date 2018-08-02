package org.hammerlab.shapeless.nesting

import org.hammerlab.shapeless.nesting

trait all
  extends seq.all
     with unroll {

  object seq extends nesting.seq.all
}
