package org.hammerlab.shapeless.tlist

import shapeless.HNil

class TListTest
  extends hammerlab.Suite {
  test("one") {
    (4: TList) should be(4 :: TNil)
    ('a: TList) should be('a :: TNil)
    ("a": TList) should be("a" :: TNil)

    4 :: TNil match {
      case 4 :: TNil ⇒
      case _ ⇒ fail()
    }
  }

  test("two") {
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

  test("three") {
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

  test("basic values / types") {

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
      TL <: TList.Aux[Int],
      Zipped <: TList.Aux[(Int, Int)]
    ](
      l: TL,
      r: TL
    )(
      implicit
      zip: Zip.Aux[TL, TL, Zipped],
      map: Map.Aux[Zipped, (Int, Int), Int, TL],
      toList: ToList[Int, TL]
    ):
      List[Int] =
      l
        .zip(r)
        .map { case (l, r) ⇒ l + r }
        .toList

    elemSum(
       1 ::  2 :: TNil,
      10 :: 20 :: TNil
    ) should be(
      11 :: 22 :: Nil
    )

//    def elemSum2[
//      L,
//      R,
//      TL <: TList.Aux[Int],
//      Zipped <: TList.Aux[(Int, Int)]
//    ](
//      l: L,
//      r: R
//    )(
//      implicit
//      ltl: IsTList.Aux[L, Int, TL],
//      rtl: IsTList.Aux[R, Int, TL],
//      zip: Zip.Aux[TL, TL, Zipped],
//      map: Map.Aux[Zipped, (Int, Int), Int, TL],
//      toList: ToList[Int, TL]
//    ):
//      List[Int] =
//      ltl(l)
//        .zip(rtl(r))(zip)
//        .map { case (l, r) ⇒ l + r }
//        .toList
//
//    elemSum2(
//      ( 1,  2),
//      (10, 20)
//    ) should be(
//      11 :: 22 :: Nil
//    )
  }

  !![IsTList[(Int, Int), Int]]

//  IsTList.baseHList[Int]
  !![IsTList[shapeless.::[Int, HNil], Int]]

  !![IsTList.Aux[shapeless.::[Int, HNil], Int, Int :: TNil]]

  IsTList.consHList[Int, shapeless.::[Int, HNil], Int :: TNil]

  !![IsTList[shapeless.::[Int, shapeless.::[Int, HNil]], Int]]
  !![IsTList.Aux[shapeless.::[Int, shapeless.::[Int, HNil]], Int, Int :: Int :: TNil]]

  !![IsTList.Aux[(Int, Int), Int, Int :: Int :: TNil]]
}
