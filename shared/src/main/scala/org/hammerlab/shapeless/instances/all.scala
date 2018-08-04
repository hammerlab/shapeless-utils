package org.hammerlab.shapeless.instances

import org.hammerlab.shapeless.instances

trait all {
  type Instances[T] = instances.Instances[T]
   val Instances    = instances.Instances

  object singleton {
    type Singleton[T] = instances.Singleton[T]
     val singleton    = instances.Singleton
  }
}
