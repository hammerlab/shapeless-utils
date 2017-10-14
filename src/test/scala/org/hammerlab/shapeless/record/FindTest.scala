package org.hammerlab.shapeless.record

import org.hammerlab.shapeless.Utils
import org.hammerlab.test.Suite
import shapeless._
import shapeless.ops.record.Selector

class FindTest
  extends Suite {

  import Utils._

  Selector[lga.Repr, Witness.`'n`.T]
  Selector[lgb.Repr, Witness.`'s`.T]

  implicitly[Find[A, Witness.`'n`.T]]
  implicitly[Find.Aux[A, Witness.`'n`.T, Int]]


  Find[B, Witness.`'s`.T]

  Find[C, Witness.`'a`.T]

  Find[lgc.Repr, Witness.`'b`.T]
  Find[C, Witness.`'b`.T]

  Find[C, Witness.`'n`.T]
  Find[C, Witness.`'s`.T]

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
    import Find.makeFindOps

    aa.find('n) should be(aa.n)

    b.find('s) should be(b.s)

    c.find('n) should be(aa.n)
    c.find('s) should be(b.s)

    d.find('b) should be(d.b)

    e.find('b) should be(b)
    e.find('c) should be(c)
    e.find('d) should be(d)
    e.find('s) should be(b.s)
  }
}
