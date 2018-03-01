package org.hammerlab.shapeless.coproduct

import org.hammerlab.Suite

object CastTest {
  sealed trait X
  case class Y(n: Int, s: String) extends X
  case class Z(n: Int, s: String) extends X
}

class CastTest
  extends Suite {

  import CastTest._
  import hammerlab.shapeless._
  import shapeless._

  test("apply") {
    val y = Y(111, "abc")
    val z = Z(222, "def")

    val xs = Seq[X](y, z)

    y map {
      case n :: s :: ⊥ ⇒
        2*n :: s.reverse :: ⊥
    } should be(
      Y(222, "cba")
    )

    xs map {
      _ map {
        case n :: s :: ⊥ ⇒
          10*n :: s*2 :: ⊥
      }
    } should be(
      Seq(
        Y(1110, "abcabc"),
        Z(2220, "defdef")
      )
    )
  }
}