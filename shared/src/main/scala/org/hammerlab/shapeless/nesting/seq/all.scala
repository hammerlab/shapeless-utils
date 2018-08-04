package org.hammerlab.shapeless.nesting.seq

import org.hammerlab.shapeless.nesting.seq

trait all {
  type Nested[T] = seq.Nested[T]
   val Nested    = seq.Nested
}
