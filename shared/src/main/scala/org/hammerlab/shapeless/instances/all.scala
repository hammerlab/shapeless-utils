package org.hammerlab.shapeless.instances

import org.hammerlab.shapeless.instances

trait all {
  type Instances[T] = instances.Instances[T]
   val Instances    = instances.Instances

  type InstanceMap[T] = instances.InstanceMap[T]
   val InstanceMap    = instances.InstanceMap

  object singleton {
    type Singleton[T] = instances.Singleton[T]
     val Singleton    = instances.Singleton
  }
}
