package org.hammerlab.shapeless.record

import org.hammerlab.shapeless._
import org.hammerlab.test.Suite
import shapeless.ops.record.Selector
import shapeless.{ Witness ⇒ W }

class FindTest
  extends Suite {

  import Utils._

  test("summon") {
    Find[lga.Repr, W.`'n`.T, Int].apply(lga.to(aa)) should be(aa.n)

    Find[A, W.`'n`.T, Int].apply(aa) should be(aa.n)

    Find[B, W.`'s`.T, String].apply(b) should be(b.s)

    Find[C, W.`'a`.T, A].apply(c) should be(c.a)
    Find[C, W.`'b`.T, B].apply(c) should be(c.b)

    Find[C, W.`'n`.T, Int].apply(c) should be(c.a.n)
    Find[C, W.`'s`.T, String].apply(c) should be(c.b.s)

    Find[E, W.`'a2`.T, A].apply(e) should be(e.a2)
    Find[E, W.`'c`.T, C].apply(e) should be(e.c)
    Find[E, W.`'d`.T, D].apply(e) should be(e.d)

    Find[E, W.`'b`.T, B].apply(e) should be(e.c.b)
    Find[E, W.`'b`.T, Boolean].apply(e) should be(e.d.b)

    Find[E, W.`'s`.T, String].apply(e) should be(e.c.b.s)
  }

  test("ops") {
    aa.find[Int]('n) should be(aa.n)

    b.find[String]('s) should be(b.s)

    c.find[A]('a) should be(aa)
    c.find[B]('b) should be(b)

    c.find[Int]('n) should be(aa.n)
    c.find[String]('s) should be(b.s)

    d.find[Boolean]('b) should be(d.b)

    e.find[A]('a2) should be(e.a2)  // unique name, non-unique type
    e.find[C]('c) should be(e.c)
    e.find[D]('d) should be(e.d)

    e.find[B]('b) should be(e.c.b)        // unique type, non-unique name
    e.find[Boolean]('b) should be(e.d.b)  // unique type, non-unique name

    e.find[String]('s) should be(e.c.b.s)
  }
}
