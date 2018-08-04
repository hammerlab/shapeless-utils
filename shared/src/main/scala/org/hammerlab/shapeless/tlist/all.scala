package org.hammerlab.shapeless.tlist

import org.hammerlab.shapeless.tlist

trait all {
  type TList = tlist.TList
   val TList = tlist.TList

  type TNil = tlist.TNil
   val TNil = tlist.TNil

  type ::[_T, Tail <: TList] = tlist.::[_T, Tail]
   val :: = tlist.::

  type IsTList[T, Elem] = tlist.IsTList[T, Elem]
   val IsTList = tlist.IsTList

  type Map[InList <: TList, Out] = tlist.Map[InList, Out]
   val Map = tlist.Map

  type Prepend[H, T <: TList] = tlist.Prepend[H, T]
   val Prepend = tlist.Prepend

  type Zip[L <: TList, R <: TList] = tlist.Zip[L, R]
   val Zip = tlist.Zip
  @inline implicit def ShapelessTListZipOps[L <: TList](l: L): Zip.Ops[L] = Zip.Ops(l)
}
