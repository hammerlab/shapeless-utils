package org.hammerlab.shapeless.tlist

import org.hammerlab.shapeless.tlist.TList.{ Base, Cons }
import shapeless.nat._

class TListTest
  extends hammerlab.Suite {
  test("one") {
    (4: TList[Int, _1]).head should be(4)
    ("abc": TList[String, _1]).head should be("abc")
  }

  test("two") {
    val t = (1, 2): TList[Int, _2]
    ==(t.head, 1)
    t match {
      case Cons(1, Base(2)) ⇒
      case _ ⇒ fail(s"$t")
    }

    val s: TList[String, _2] = ("", "abc")
    ==(s.head, "")
    s match {
      case Cons("", Base("abc")) ⇒
      case _ ⇒ fail(s"$s")
    }
  }

  test("three") {
    val t = (1, 2, 3): TList[Int, _3]
    ==(t.head, 1)
    t match {
      case Cons(1, Cons(2, Base(3))) ⇒
      case _ ⇒ fail(s"$t")
    }

    val s: TList[String, _3] = ("", "abc", "zzz")
    ==(s.head, "")
    s match {
      case Cons("", Cons("abc", Base("zzz"))) ⇒
      case _ ⇒ fail(s"$s")
    }
  }
}
