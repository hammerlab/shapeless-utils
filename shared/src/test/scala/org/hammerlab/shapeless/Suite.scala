package org.hammerlab.shapeless

class Suite
  extends hammerlab.Suite
     with all
     with Utils {
  val !! = shapeless.the
}
