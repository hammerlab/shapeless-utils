package org.hammerlab.shapeless.record

import org.hammerlab.shapeless.Suite
import shapeless.{ Witness ⇒ W }

class FindTest
  extends Suite {

  test("summon") {
    ==(Find[lga.Repr, W.`'n`.T, Int].apply(lga.to(_a)), _a.n)

    ==(Find[A, W.`'n`.T, Int].apply(_a), _a.n)

    ==(Find[B, W.`'s`.T, String].apply(b), b.s)

    ==(Find[C, W.`'a`.T, A].apply(c), c.a)
    ==(Find[C, W.`'b`.T, B].apply(c), c.b)

    ==(Find[C, W.`'n`.T, Int].apply(c), c.a.n)
    ==(Find[C, W.`'s`.T, String].apply(c), c.b.s)

    ==(Find[D, W.`'b`.T, Boolean].apply(d), d.b)

    ==(Find[E, W.`'a2`.T, A].apply(e), e.a2)
    ==(Find[E, W.`'c`.T, C].apply(e), e.c)
    ==(Find[E, W.`'d`.T, D].apply(e), e.d)

    ==(Find[E, W.`'b`.T, B].apply(e), e.c.b)
    ==(Find[E, W.`'b`.T, Boolean].apply(e), e.d.b)

    ==(Find[E, W.`'s`.T, String].apply(e), e.c.b.s)

    ==(Find[F, W.`'e`.T, E].apply(f), f.e)

    ==(Find[F, W.`'a2`.T, A].apply(f), f.e.a2)
    ==(Find[F, W.`'c`.T, C].apply(f), f.e.c)
    ==(Find[F, W.`'d`.T, D].apply(f), f.e.d)

    ==(Find[F, W.`'b`.T, B].apply(f), f.e.c.b)
    ==(Find[F, W.`'b`.T, Boolean].apply(f), f.e.d.b)

    ==(Find[F, W.`'s`.T, String].apply(f), f.e.c.b.s)
  }

  test("ops") {
    ==(_a.find[    Int]('n ), _a.n      )

    ==( b.find[ String]('s ),  b.s      )

    ==( c.find[      A]('a ), _a        )
    ==( c.find[      B]('b ),  b        )

    ==( c.find[    Int]('n ), _a.n      )
    ==( c.find[ String]('s ),  b.s      )

    ==( d.find[Boolean]('b ),  d.b      )

    ==( e.find[      A]('a2),  e.a2     )  // unique name, non-unique type
    ==( e.find[      C]('c ),  e.c      )
    ==( e.find[      D]('d ),  e.d      )

    ==( e.find[      B]('b ),  e.c.b    )  // unique type, non-unique name
    ==( e.find[Boolean]('b ),  e.d.b    )  // unique type, non-unique name

    ==( e.find[ String]('s ),  e.c.b.s  )

    ==( f.find[      E]('e ),  f.e      )

    ==( f.find[      A]('a2),  f.e.a2   )  // unique name, non-unique type
    ==( f.find[      C]('c ),  f.e.c    )
    ==( f.find[      D]('d ),  f.e.d    )

    ==( f.find[      B]('b ),  f.e.c.b  )  // unique type, non-unique name
    ==( f.find[Boolean]('b ),  f.e.d.b  )  // unique type, non-unique name

    ==( f.find[ String]('s ),  f.e.c.b.s)
  }
}
