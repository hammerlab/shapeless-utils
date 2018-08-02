package org.hammerlab.shapeless

class Suite
  extends hammerlab.Suite
     with all
     with Utils {
  def !![T](implicit t: T): T = t
}
