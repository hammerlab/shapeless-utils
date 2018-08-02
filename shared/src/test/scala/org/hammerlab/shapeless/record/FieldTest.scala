package org.hammerlab.shapeless.record

import hammerlab.shapeless._
import org.hammerlab.shapeless.Suite
import shapeless.{ Witness ⇒ W }

class FieldTest
  extends Suite {

  test("summon") {
    ==(Field[lga.Repr, W.`'n`.T].apply(lga.to(_a)), _a.n)

    ==(Field[A, W.`'n`. T].apply(_a), _a.n)

    ==(Field[B, W.`'s`. T].apply( b),  b.s)

    ==(Field[C, W.`'a`. T].apply( c),  c.a)
    ==(Field[C, W.`'b`. T].apply( c),  c.b)

    ==(Field[C, W.`'n`. T].apply( c),  c.a.n)
    ==(Field[C, W.`'s`. T].apply( c),  c.b.s)

    ==(Field[D, W.`'b`. T].apply( d),  d.b)

    ==(Field[E, W.`'a2`.T].apply( e),  e.a2)
    ==(Field[E, W.`'c` .T].apply( e),  e.c)
    ==(Field[E, W.`'d` .T].apply( e),  e.d)

    ==(Field[E, W.`'b` .T].apply( e),  e.c.b)

    ==(Field[E, W.`'s` .T].apply( e),  e.c.b.s)

    ==(Field[F, W.`'e` .T].apply( f),  f.e)

    ==(Field[F, W.`'a2`.T].apply( f),  f.e.a2)
    ==(Field[F, W.`'c` .T].apply( f),  f.e.c)
    ==(Field[F, W.`'d` .T].apply( f),  f.e.d)

    ==(Field[F, W.`'b` .T].apply( f),  f.e.c.b)

    ==(Field[F, W.`'s` .T].apply( f),  f.e.c.b.s)
  }

  /**
   * Not sure why, but summoning [[Field.Aux]] instances doesn't work; [[Find]] is suitable for receiving as an implicit
   * parameter to a method that wants to pull a known field out of an object by its key and value.
   */
  /*
  test("summon aux") {
    ==(Field.Aux[lga.Repr, W.`'n`.T, Int].apply(lga.to(aa)), aa.n)

    ==(Field.Aux[A, W.`'n`.T, Int].apply(aa), aa.n)

    ==(Field.Aux[B, W.`'s`.T, String].apply(b), b.s)

    ==(Field.Aux[C, W.`'a`.T, A].apply(c), c.a)
    ==(Field.Aux[C, W.`'b`.T, B].apply(c), c.b)

    ==(Field.Aux[C, W.`'n`.T, Int].apply(c), c.a.n)
    ==(Field.Aux[C, W.`'s`.T, String].apply(c), c.b.s)

    ==(Field.Aux[E, W.`'a2`.T, A].apply(e), e.a2)
    ==(Field.Aux[E, W.`'c`.T, C].apply(e), e.c)
    ==(Field.Aux[E, W.`'d`.T, D].apply(e), e.d)

    ==(Field.Aux[E, W.`'b`.T, B].apply(e), e.c.b)
    ==(Field.Aux[E, W.`'b`.T, Boolean].apply(e), e.d.b)

    ==(Field.Aux[E, W.`'s`.T, String].apply(e), e.c.b.s)
  }
*/

  test("ops") {
    ==(_a.field('n ), _a.n)

    ==( b.field('s ),  b.s)

    ==( c.field('a ), _a)
    ==( c.field('b ),  b)

    ==( c.field('n ), _a.n)
    ==( c.field('s ),  b.s)

    ==( d.field('b ),  d.b)

    ==( e.field('a2),  e.a2)  // unique name, non-unique type
    ==( e.field('c ),  e.c)
    ==( e.field('d ),  e.d)

    ==( e.field('s ),  e.c.b.s)

    ==( f.field('e ),  f.e)

    ==( f.field('a2),  f.e.a2)  // unique name, non-unique type
    ==( f.field('c ),  f.e.c)
    ==( f.field('d ),  f.e.d)

    ==( f.field('b ),  f.e.c.b)

    ==( f.field('s ),  f.e.c.b.s)
  }
}
