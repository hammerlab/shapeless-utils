package org.hammerlab.shapeless.hlist

import org.hammerlab.shapeless._

class FindTest
  extends Suite {

  test("summons") {
    implicitly[Find[A, Int]].apply(aa) should be(aa.n)
    implicitly[Find[B, String]].apply(b) should be(b.s)

    implicitly[Find[C, A]].apply(c) should be(c.a)
    implicitly[Find[C, B]].apply(c) should be(c.b)
    implicitly[Find[C, Int]].apply(c) should be(c.a.n)
    implicitly[Find[C, String]].apply(c) should be(c.b.s)

    implicitly[Find[D, Boolean]].apply(d) should be(d.b)

    implicitly[Find[E, B]].apply(e) should be(e.c.b)
    implicitly[Find[E, C]].apply(e) should be(e.c)
    implicitly[Find[E, D]].apply(e) should be(e.d)
    implicitly[Find[E, Boolean]].apply(e) should be(e.d.b)
    implicitly[Find[E, String]].apply(e) should be(e.c.b.s)

    implicitly[Find[F, E]].apply(f) should be(f.e)
    implicitly[Find[F, B]].apply(f) should be(f.e.c.b)
    implicitly[Find[F, C]].apply(f) should be(f.e.c)
    implicitly[Find[F, D]].apply(f) should be(f.e.d)
    implicitly[Find[F, Boolean]].apply(f) should be(f.e.d.b)
    implicitly[Find[F, String]].apply(f) should be(f.e.c.b.s)
  }

  test("ops") {
    aa.findt[Int] should be(aa.n)
    b.findt[String] should be(b.s)

    c.findt[A] should be(c.a)
    c.findt[B] should be(c.b)
    c.findt[Int] should be(c.a.n)
    c.findt[String] should be(c.b.s)

    d.findt[Boolean] should be(d.b)

    e.findt[B] should be(e.c.b)
    e.findt[C] should be(e.c)
    e.findt[D] should be(e.d)
    e.findt[Boolean] should be(e.d.b)
    e.findt[String] should be(e.c.b.s)

    f.findt[E] should be(f.e)
    f.findt[B] should be(f.e.c.b)
    f.findt[C] should be(f.e.c)
    f.findt[D] should be(f.e.d)
    f.findt[Boolean] should be(f.e.d.b)
    f.findt[String] should be(f.e.c.b.s)
  }

  test("stand-alone findt") {
    implicit val _f = f

    findt[F, E] should be(f.e)
    findt[F, B] should be(f.e.c.b)
    findt[F, C] should be(f.e.c)
    findt[F, D] should be(f.e.d)
    findt[F, Boolean] should be(f.e.d.b)
    findt[F, String] should be(f.e.c.b.s)
  }
}
