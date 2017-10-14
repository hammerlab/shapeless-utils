package org.hammerlab.shapeless.record

import org.hammerlab.shapeless.Utils
import org.hammerlab.test.Suite
import shapeless._
import shapeless.ops.record.Selector
import org.hammerlab.shapeless._

class FindTest
  extends Suite {

  import Utils._

  test("summon") {
    Selector[lga.Repr, Witness.`'n`.T].apply(lga.to(aa)) should be(aa.n)

    Find[lga.Repr, Witness.`'n`.T]
    Find[lga.Repr, Witness.`'n`.T].apply(lga.to(aa)) should be(aa.n)
    Find[A, Witness.`'n`.T].apply(aa) should be(aa.n)

    Find[C, Witness.`'a`.T].apply(c) should be(aa)

    Find[C, Witness.`'n`.T].apply(c) should be(aa.n)
    Find[C, Witness.`'s`.T].apply(c) should be(b.s)
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
