package hammerlab

import org.hammerlab.shapeless.all
import org.hammerlab.shapeless.nat.implicits

object shapeless
  extends all {
    type nat = implicits
  object nat extends nat
}
