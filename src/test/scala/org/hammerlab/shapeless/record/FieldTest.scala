package org.hammerlab.shapeless.record

import org.hammerlab.shapeless.Utils
import org.hammerlab.test.Suite
import shapeless.{ Witness ⇒ W }

class FieldTest
  extends Suite {

  import Utils._

  test("summon") {
    Field[lga.Repr, W.`'n`.T].apply(lga.to(aa)) should be(aa.n)

    Field[A, W.`'n`.T].apply(aa) should be(aa.n)

    Field[B, W.`'s`.T].apply(b) should be(b.s)

    Field[C, W.`'a`.T].apply(c) should be(c.a)
    Field[C, W.`'b`.T].apply(c) should be(c.b)

    Field[C, W.`'n`.T].apply(c) should be(c.a.n)
    Field[C, W.`'s`.T].apply(c) should be(c.b.s)

    Field[D, W.`'b`.T].apply(d) should be(d.b)

    Field[E, W.`'a2`.T].apply(e) should be(e.a2)
    Field[E, W.`'c`.T].apply(e) should be(e.c)
    Field[E, W.`'d`.T].apply(e) should be(e.d)

    Field[E, W.`'b`.T].apply(e) should be(e.c.b)

    Field[E, W.`'s`.T].apply(e) should be(e.c.b.s)

    Field[F, W.`'e`.T].apply(f) should be(f.e)

    Field[F, W.`'a2`.T].apply(f) should be(f.e.a2)
    Field[F, W.`'c`.T].apply(f) should be(f.e.c)
    Field[F, W.`'d`.T].apply(f) should be(f.e.d)

    Field[F, W.`'b`.T].apply(f) should be(f.e.c.b)

    Field[F, W.`'s`.T].apply(f) should be(f.e.c.b.s)
  }

  /**
   * Not sure why, but summoning [[Field.Aux]] instances doesn't work; [[Find]] is suitable for receiving as an implicit
   * parameter to a method that wants to pull a known field out of an object by its key and value.
   */
  /*
  test("summon aux") {
    Field.Aux[lga.Repr, W.`'n`.T, Int].apply(lga.to(aa)) should be(aa.n)

    Field.Aux[A, W.`'n`.T, Int].apply(aa) should be(aa.n)

    Field.Aux[B, W.`'s`.T, String].apply(b) should be(b.s)

    Field.Aux[C, W.`'a`.T, A].apply(c) should be(c.a)
    Field.Aux[C, W.`'b`.T, B].apply(c) should be(c.b)

    Field.Aux[C, W.`'n`.T, Int].apply(c) should be(c.a.n)
    Field.Aux[C, W.`'s`.T, String].apply(c) should be(c.b.s)

    Field.Aux[E, W.`'a2`.T, A].apply(e) should be(e.a2)
    Field.Aux[E, W.`'c`.T, C].apply(e) should be(e.c)
    Field.Aux[E, W.`'d`.T, D].apply(e) should be(e.d)

    Field.Aux[E, W.`'b`.T, B].apply(e) should be(e.c.b)
    Field.Aux[E, W.`'b`.T, Boolean].apply(e) should be(e.d.b)

    Field.Aux[E, W.`'s`.T, String].apply(e) should be(e.c.b.s)
  }
*/

  test("ops") {
    aa.field('n) should be(aa.n)

    b.field('s) should be(b.s)

    c.field('a) should be(aa)
    c.field('b) should be(b)

    c.field('n) should be(aa.n)
    c.field('s) should be(b.s)

    d.field('b) should be(d.b)

    e.field('a2) should be(e.a2)  // unique name, non-unique type
    e.field('c) should be(e.c)
    e.field('d) should be(e.d)

    e.field('s) should be(e.c.b.s)

    f.field('e) should be(f.e)

    f.field('a2) should be(f.e.a2)  // unique name, non-unique type
    f.field('c) should be(f.e.c)
    f.field('d) should be(f.e.d)

    f.field('b) should be(f.e.c.b)

    f.field('s) should be(f.e.c.b.s)
  }
}
