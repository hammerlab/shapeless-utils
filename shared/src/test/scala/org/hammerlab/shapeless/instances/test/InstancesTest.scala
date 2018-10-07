package org.hammerlab.shapeless.instances.test

import java.nio

import cats.Show
import org.hammerlab.shapeless.instances.test.InstancesTest.ByteOrder._
import org.hammerlab.shapeless.instances.test.InstancesTest._
import hammerlab.shapeless.instances._
import hammerlab.shapeless.instances.singleton._
import shapeless.HNil

object InstancesTest {
  case class Foo[T](bo: ByteOrder, dt: DType)

  sealed abstract class ByteOrder(override val toString: String)
  object ByteOrder {

    sealed abstract class Endianness(override val toString: String)
      extends ByteOrder(toString)

    case object LittleEndian extends Endianness("<")
    case object    BigEndian extends Endianness(">")

    case object         None extends  ByteOrder("|")

    object Endianness {
      implicit def toByteOrder(endianness: Endianness): java.nio.ByteOrder =
        endianness match {
          case LittleEndian ⇒ nio.ByteOrder.LITTLE_ENDIAN
          case    BigEndian ⇒ nio.ByteOrder.   BIG_ENDIAN
        }
    }
  }

  sealed abstract class DType(override val toString: String)
  object DType {
    case object     int extends DType("i")
    case object    bool extends DType("b")
    case object   float extends DType("f")
    case object  string extends DType("s")
    case object unicode extends DType("u")
  }
}

class InstancesTest
  extends hammerlab.Suite {

  import DType._

  test("singletons") {
    ==(Singleton[int.type](), int)
    ==(Singleton[None.type](), None)
  }

  val > = BigEndian
  val < = LittleEndian
  val | = None

  val b = bool
  val f = float
  val i = int
  val s = string
  val u = unicode

  test("instances") {
    Instances[DType]() should be(
         bool ::
        float ::
          int ::
       string ::
      unicode ::
         HNil
    )

    Instances[ByteOrder]() should be(
         BigEndian ::
      LittleEndian ::
              None ::
              HNil
    )

    !![Instances[Foo[_]]].apply() should be(
      (> :: b :: HNil) ::
      (> :: f :: HNil) ::
      (> :: i :: HNil) ::
      (> :: s :: HNil) ::
      (> :: u :: HNil) ::
      (< :: b :: HNil) ::
      (< :: f :: HNil) ::
      (< :: i :: HNil) ::
      (< :: s :: HNil) ::
      (< :: u :: HNil) ::
      (| :: b :: HNil) ::
      (| :: f :: HNil) ::
      (| :: i :: HNil) ::
      (| :: s :: HNil) ::
      (| :: u :: HNil) ::
      HNil
    )
  }

  test("map") {
    ==(
      InstanceMap[ByteOrder](),
      Map(
        ">" → >,
        "<" → <,
        "|" → |
      )
    )

    {
      implicit val show: Show[ByteOrder] =
        new Show[ByteOrder] {
          override def show(t: ByteOrder): String =
            t match {
              case BigEndian ⇒ "BE"
              case LittleEndian ⇒ "LE"
              case None ⇒ "-"
            }
        }
      ==(
        InstanceMap[ByteOrder](),
        Map(
          "BE" → >,
          "LE" → <,
           "-" → |
        )
      )
    }
  }
}

