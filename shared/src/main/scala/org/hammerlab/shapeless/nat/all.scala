package org.hammerlab.shapeless.nat

import shapeless.Nats

trait all
  extends implicits
     with Nats {
  type Nat = shapeless.Nat
  type _0 = shapeless._0
   val _0 = shapeless.nat._0
}
