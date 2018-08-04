package org.hammerlab.shapeless.tlist

import shapeless.HNil

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
}
