package org.hammerlab.shapeless.hlist

import hammerlab.shapeless._
import org.hammerlab.shapeless.Suite

class FindTest
  extends Suite {

  test("summons") {
    ==(implicitly[Find[A,     Int]].apply(_a), _a.n)
    ==(implicitly[Find[B,  String]].apply( b),  b.s)

    ==(implicitly[Find[C,       A]].apply( c),  c.a)
    ==(implicitly[Find[C,       B]].apply( c),  c.b)
    ==(implicitly[Find[C,     Int]].apply( c),  c.a.n)
    ==(implicitly[Find[C,  String]].apply( c),  c.b.s)

    ==(implicitly[Find[D, Boolean]].apply( d), d.b)

    ==(implicitly[Find[E,       B]].apply( e),  e.c.b)
    ==(implicitly[Find[E,       C]].apply( e),  e.c)
    ==(implicitly[Find[E,       D]].apply( e),  e.d)
    ==(implicitly[Find[E, Boolean]].apply( e),  e.d.b)
    ==(implicitly[Find[E,  String]].apply( e),  e.c.b.s)

    ==(implicitly[Find[F,       E]].apply( f),  f.e)
    ==(implicitly[Find[F,       B]].apply( f),  f.e.c.b)
    ==(implicitly[Find[F,       C]].apply( f),  f.e.c)
    ==(implicitly[Find[F,       D]].apply( f),  f.e.d)
    ==(implicitly[Find[F, Boolean]].apply( f),  f.e.d.b)
    ==(implicitly[Find[F,  String]].apply( f),  f.e.c.b.s)
  }

  test("ops") {
    ==(_a.findt[    Int], _a.n)
    ==( b.findt[ String],  b.s)

    ==( c.findt[      A],  c.a)
    ==( c.findt[      B],  c.b)
    ==( c.findt[    Int],  c.a.n)
    ==( c.findt[ String],  c.b.s)

    ==( d.findt[Boolean],  d.b)

    ==( e.findt[      B],  e.c.b)
    ==( e.findt[      C],  e.c)
    ==( e.findt[      D],  e.d)
    ==( e.findt[Boolean],  e.d.b)
    ==( e.findt[ String],  e.c.b.s)

    ==( f.findt[      E],  f.e)
    ==( f.findt[      B],  f.e.c.b)
    ==( f.findt[      C],  f.e.c)
    ==( f.findt[      D],  f.e.d)
    ==( f.findt[Boolean],  f.e.d.b)
    ==( f.findt[ String],  f.e.c.b.s)
  }

  test("stand-alone findt") {
    implicit val _f = f

    ==(findt[F,       E], f.e)
    ==(findt[F,       B], f.e.c.b)
    ==(findt[F,       C], f.e.c)
    ==(findt[F,       D], f.e.d)
    ==(findt[F, Boolean], f.e.d.b)
    ==(findt[F,  String], f.e.c.b.s)
  }
}
