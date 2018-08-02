package org.hammerlab.shapeless.nesting

import org.hammerlab.shapeless.nesting
trait all {
  type Unroll[In, T[_]] = nesting.Unroll[In, T]
   val Unroll = nesting.Unroll
}
