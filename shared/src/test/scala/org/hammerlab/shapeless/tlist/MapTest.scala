package org.hammerlab.shapeless.tlist

class MapTest
  extends hammerlab.Suite {
  test("maps") {
    (1 to 10).map(_ * 2)

    !![Map.Aux[TNil, Int, Int, TNil]]

    val l = 1 :: 2 :: 3 :: TNil
    !![Map[TNil, Int]].apply(TNil, _ * 2) should be(TNil)
    !![Map[TNil, String]].apply(TNil, _.toString) should be(TNil)

    !![Map[Int :: Int :: Int :: TNil, Int]].apply(l, _ * 2) should be(2 :: 4 :: 6 :: TNil)
    !![Map[Int :: Int :: Int :: TNil, String]].apply(l, _.toString) should be("1" :: "2" :: "3" :: TNil)

    !![Map.Ax[TNil, Int, Int]]
    !![Map.Aux[TNil, Int, Int, TNil]]

    TNil.map((_: Int) * 2) should be(TNil)
    TNil.map(_.toString) should be(TNil)

    l.map(_ * 2) should be(2 :: 4 :: 6 :: TNil)
    l.map(_.toString) should be("1" :: "2" :: "3" :: TNil)
  }
}
