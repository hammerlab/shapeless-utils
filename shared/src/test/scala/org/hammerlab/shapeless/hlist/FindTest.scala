package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless.Suite

class FindTest
  extends Suite {

  test("summons") {
    ==(!![Find[A,     Int]].apply(_a), _a.n)
    ==(!![Find[B,  String]].apply( b),  b.s)

    ==(!![Find[C,       A]].apply( c),  c.a)
    ==(!![Find[C,       B]].apply( c),  c.b)
    ==(!![Find[C,     Int]].apply( c),  c.a.n)
    ==(!![Find[C,  String]].apply( c),  c.b.s)

    ==(!![Find[D, Boolean]].apply( d), d.b)

    ==(!![Find[E,       B]].apply( e),  e.c.b)
    ==(!![Find[E,       C]].apply( e),  e.c)
    ==(!![Find[E,       D]].apply( e),  e.d)
    ==(!![Find[E, Boolean]].apply( e),  e.d.b)
    ==(!![Find[E,  String]].apply( e),  e.c.b.s)

    ==(!![Find[F,       E]].apply( f),  f.e)
    ==(!![Find[F,       B]].apply( f),  f.e.c.b)
    ==(!![Find[F,       C]].apply( f),  f.e.c)
    ==(!![Find[F,       D]].apply( f),  f.e.d)
    ==(!![Find[F, Boolean]].apply( f),  f.e.d.b)
    ==(!![Find[F,  String]].apply( f),  f.e.c.b.s)
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
