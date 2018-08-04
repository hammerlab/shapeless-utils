package org.hammerlab.shapeless.tlist

import org.hammerlab.shapeless.tlist

trait all
  extends HasToList
     with HasZip {
  type TList = tlist.TList
   val TList = tlist.TList

  type TNil = tlist.TNil
   val TNil = tlist.TNil

  type ::[_T, Tail <: TList] = tlist.::[_T, Tail]
   val :: = tlist.::

  type IsTList[T] = tlist.IsTList[T]
   val IsTList = tlist.IsTList

  type Map[InList <: TList, Out] = tlist.Map[InList, Out]
   val Map = tlist.Map

  type Prepend[H, T <: TList] = tlist.Prepend[H, T]
   val Prepend = tlist.Prepend

  object syntax
    extends HasToListOps
       with HasZipOps
}
