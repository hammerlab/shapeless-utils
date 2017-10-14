package org.hammerlab.shapeless.record

import org.hammerlab.shapeless._
import org.hammerlab.test.Suite
import shapeless.ops.record.Selector
import shapeless.{ Witness ⇒ W }

class FindTest
  extends Suite {

  import Utils._

  test("summon") {
    Selector[lga.Repr, W.`'n`.T].apply(lga.to(aa)) should be(aa.n)

    Find[lga.Repr, W.`'n`.T]
    Find[lga.Repr, W.`'n`.T].apply(lga.to(aa)) should be(aa.n)
    Find[A, W.`'n`.T].apply(aa) should be(aa.n)

    Find[C, W.`'a`.T].apply(c) should be(c.a)
    Find[C, W.`'b`.T].apply(c) should be(c.b)

    Find[C, W.`'n`.T].apply(c) should be(c.a.n)
    Find[C, W.`'s`.T].apply(c) should be(c.b.s)

    Find[D, W.`'b`.T].apply(d) should be(d.b)

    Find[E, W.`'c`.T].apply(e) should be(e.c)
    Find[E, W.`'a2`.T].apply(e) should be(e.a2)
    Find[E, W.`'b`.T].apply(e) should be(e.c.b)
    Find[E, W.`'d`.T].apply(e) should be(e.d)
    Find[E, W.`'s`.T].apply(e) should be(e.c.b.s)

    Find.Aux[C, W.`'s`.T, String].apply(c) should be(c.b.s)
    implicitly[Find.Aux[C, W.`'s`.T, String]].apply(c) should be(c.b.s)

    Find.Aux[E, W.`'s`.T, String].apply(c) should be(e.c.b.s)
    implicitly[Find.Aux[E, W.`'s`.T, String]].apply(c) should be(e.c.b.s)
  }

  test("ops") {
    aa.find('n) should be(aa.n)

    b.find('s) should be(b.s)

    c.find('n) should be(aa.n)
    c.find('s) should be(b.s)

    d.find('b) should be(d.b)

    e.find('b) should be(b)
    e.find('c) should be(c)
    e.find('d) should be(d)
    e.find('s) should be(b.s)
    e.find('a2) should be(e.a2)
  }
}
