package org.hammerlab.shapeless.tlist

import org.hammerlab.shapeless.tlist
import shapeless.Nat

trait all {
  type TList[T, N <: Nat] = tlist.TList[T, N]
   val TList = tlist.TList

  type IsTList[T, Elem, N <: Nat] = tlist.IsTList[T, Elem, N]
   val IsTList = tlist.IsTList
}
