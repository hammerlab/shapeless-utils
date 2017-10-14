package org.hammerlab.shapeless.hlist

import org.hammerlab.test.Suite
import org.hammerlab.shapeless.Utils._

class FindTest
  extends Suite {

  test("summons") {
    implicitly[Find[A, Int]].apply(aa) should be(123)
    implicitly[Find[B, String]].apply(b) should be("abc")

    implicitly[Find[C, Int]].apply(c) should be(123)
    implicitly[Find[C, String]].apply(c) should be("abc")

    implicitly[Find[D, Boolean]].apply(d) should be(true)

    implicitly[Find[E, B]].apply(e) should be(b)
    implicitly[Find[E, C]].apply(e) should be(c)
    implicitly[Find[E, D]].apply(e) should be(d)
    implicitly[Find[E, Boolean]].apply(e) should be(true)
    implicitly[Find[E, String]].apply(e) should be("abc")
  }

  test("ops") {
    aa.findt[Int] should be(123)
    b.findt[String] should be("abc")

    c.findt[Int] should be(123)
    c.findt[String] should be("abc")

    d.findt[Boolean] should be(true)

    e.findt[B] should be(b)
    e.findt[C] should be(c)
    e.findt[D] should be(d)
    e.findt[Boolean] should be(true)
    e.findt[String] should be("abc")
  }
}
