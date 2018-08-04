package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.hlist
import shapeless.HList

trait all
  extends HasFind
     with HasFlattened
     with HasSelect {

  type ElementPrepend[H, T] = hlist.ElementPrepend[H, T]
   val ElementPrepend       = hlist.ElementPrepend

  type ElemwisePrepend[E, L <: HList] = hlist.ElemwisePrepend[E, L]
   val ElemwisePrepend                = hlist.ElemwisePrepend

  type Cartesian[L <: HList, R <: HList] = hlist.Cartesian[L, R]
   val Cartesian                         = hlist.Cartesian
}
