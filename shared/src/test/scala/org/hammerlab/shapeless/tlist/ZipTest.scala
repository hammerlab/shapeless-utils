package org.hammerlab.shapeless.tlist

class ZipTest
  extends hammerlab.Suite {

  import hammerlab.shapeless.tlist.syntax._

  test("zips") {
    val l = 1 :: 2 :: 3 :: TNil
    val r = 2 :: 4 :: 6 :: TNil

    !![Zip[                     TNil,                      TNil]]
    !![Zip[              Int :: TNil,               Int :: TNil]]
    !![Zip[       Int :: Int :: TNil,        Int :: Int :: TNil]]
    !![Zip[Int :: Int :: Int :: TNil, Int :: Int :: Int :: TNil]]

    val zip0 = !![Zip.Aux[                     TNil,                      TNil,                                           TNil]]
    val zip1 = !![Zip.Aux[              Int :: TNil,               Int :: TNil,                             (Int, Int) :: TNil]]
    val zip2 = !![Zip.Aux[       Int :: Int :: TNil,        Int :: Int :: TNil,               (Int, Int) :: (Int, Int) :: TNil]]
    val zip3 = !![Zip.Aux[Int :: Int :: Int :: TNil, Int :: Int :: Int :: TNil, (Int, Int) :: (Int, Int) :: (Int, Int) :: TNil]]

    zip0(TNil, TNil) should be(TNil)

    zip1(1           :: TNil, 10             :: TNil) should be((1, 10)                       :: TNil)
    zip2(1 :: 2      :: TNil, 10 :: 20       :: TNil) should be((1, 10) :: (2, 20)            :: TNil)
    zip3(1 :: 2 :: 3 :: TNil, 10 :: 20 :: 30 :: TNil) should be((1, 10) :: (2, 20) :: (3, 30) :: TNil)

    1           :: TNil zip('a             :: TNil) should be((1, 'a)                       :: TNil)
    1 :: 2      :: TNil zip('a :: 'b       :: TNil) should be((1, 'a) :: (2, 'b)            :: TNil)
    1 :: 2 :: 3 :: TNil zip('a :: 'b :: 'c :: TNil) should be((1, 'a) :: (2, 'b) :: (3, 'c) :: TNil)
  }
}
