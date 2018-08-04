package org.hammerlab.shapeless.tlist

class TListTest
  extends hammerlab.Suite {
  test("one") {
    val t = 1 :: TNil

    ==(t.head, 1)
    ==(t.tail, TNil)

    t match {
      case 1 :: TNil ⇒
      case _ ⇒ fail()
    }
  }

  test("two") {
    {
      val t = 1 :: 2 :: TNil

      ==(t.head, 1)
      ==(t.tail, 2 :: TNil)
      ==(t.tail.head, 2)
      ==(t.tail.tail, TNil)

      t match {
        case 1 :: 2 :: TNil ⇒
        case _ ⇒ fail()
      }
    }

    {
      val t = TList((1, 2))

      ==(t.head, 1)
      ==(t.tail, 2 :: TNil)
      ==(t.tail.head, 2)
      ==(t.tail.tail, TNil)

      t match {
        case 1 :: 2 :: TNil ⇒
        case _ ⇒ fail()
      }
    }
  }

  test("three") {
    {
      val t = 1 :: 2 :: 3 :: TNil

      ==(t.head, 1)
      ==(t.tail, 2 :: 3 :: TNil)
      ==(t.tail.head, 2)
      ==(t.tail.tail, 3 :: TNil)
      ==(t.tail.tail.head, 3)
      ==(t.tail.tail.tail, TNil)

      t match {
        case 1 :: 2 :: 3 :: TNil ⇒
        case _ ⇒ fail()
      }
    }
    {
      val t = TList((1, 2, 3))

      ==(t.head, 1)
      ==(t.tail, 2 :: 3 :: TNil)
      ==(t.tail.head, 2)
      ==(t.tail.tail, 3 :: TNil)
      ==(t.tail.tail.head, 3)
      ==(t.tail.tail.tail, TNil)

      t match {
        case 1 :: 2 :: 3 :: TNil ⇒
        case _ ⇒ fail()
      }
    }
  }

  test("casts") {

    // ints:
                   TNil :                      TNil
              1 :: TNil :               Int :: TNil
         2 :: 1 :: TNil :        Int :: Int :: TNil
    3 :: 2 :: 1 :: TNil : Int :: Int :: Int :: TNil

    // strings:
                         TNil :                               TNil
                  "a" :: TNil :                     String :: TNil
           "b" :: "a" :: TNil :           String :: String :: TNil
    "c" :: "b" :: "a" :: TNil : String :: String :: String :: TNil
  }

  test("type errors") {
    illTyped("'a :: 1 :: TNil")
    illTyped("'a :: 2 :: 1 :: TNil")
    illTyped("2 :: 'a :: 1 :: TNil")
    illTyped("2 :: 3L :: 1 :: TNil")
  }

  test("syntax / evidences") {
    import hammerlab.shapeless.tlist.syntax._

    def elemSum[
          L,
          R,
          TL <: TList.Aux[      Int ],
      Zipped <: TList.Aux[(Int, Int)]
    ](
      l: L,
      r: R
    )(
      implicit
      ltl: IsTList.Aux[L, TL],
      rtl: IsTList.Aux[R, TL],
      zip: Zip.Aux[TL, TL, Zipped],
      map: Map.Aux[Zipped, (Int, Int), Int, TL],
      toList: ToList[Int, TL]
    ):
      List[Int] =
      ltl(l)
        .zip(rtl(r))(zip)
        .map { case (l, r) ⇒ l + r }
        .toList

    elemSum(
      ( 1,  2),
      (10, 20)
    ) should be(
      11 :: 22 :: Nil
    )

    elemSum(
       1 ::  2 :: TNil,
      10 :: 20 :: TNil
    ) should be(
      11 :: 22 :: Nil
    )
  }

  test("prepend ops") {
    !![Prepend[Int, TNil]]
    !![Prepend[Int, TNil.type]]
    !![Prepend[Int, Int :: TNil]]

    def prepend[H, TL <: TList](h: H, tl: TL)(implicit pp: Prepend[H, TL]) = {
      // importing this here to not interfere with TList-terms that are already in scope in this package; in general use
      // this could go at the top a file or be mixed-in to a package-object rather than inserted awkwardly at call-sites
      import hammerlab.shapeless.tlist._
      h :: tl
    }

    prepend(4, TNil) should be(4 :: TNil)
    ==(prepend(2, 4 :: TNil), 2 :: 4 :: TNil)
    illTyped("prepend('a, 4 :: TNil)")
  }
}
