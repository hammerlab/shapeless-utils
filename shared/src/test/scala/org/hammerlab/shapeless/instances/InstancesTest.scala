package org.hammerlab.shapeless.instances

import org.hammerlab.shapeless.Suite
import org.hammerlab.shapeless.instances.InstancesTest.ByteOrder._
import org.hammerlab.shapeless.instances.InstancesTest._
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
          case LittleEndian ⇒ java.nio.ByteOrder.LITTLE_ENDIAN
          case    BigEndian ⇒ java.nio.ByteOrder.BIG_ENDIAN
        }
    }
  }

  sealed abstract class DType(override val toString: String)
  object DType {
    case object     int extends DType("i")
    case object    bool extends DType("b")
    case object   float extends DType("f")
    case object  string extends DType("S")
    case object unicode extends DType("U")
  }
}

class InstancesTest
  extends Suite {

  test("instances") {
    import DType._
    implicitly[Instances[DType]].apply() should be(bool :: float :: int :: string :: unicode :: HNil)
    implicitly[Instances[ByteOrder]].apply() should be(BigEndian :: LittleEndian :: None :: HNil)

    val > = BigEndian
    val < = LittleEndian
    val | = None

    val b = bool
    val f = float
    val i = int
    val S = string
    val U = unicode

    implicitly[Instances[Foo[_]]].apply() should be(
      (> :: b :: HNil) ::
      (> :: f :: HNil) ::
      (> :: i :: HNil) ::
      (> :: S :: HNil) ::
      (> :: U :: HNil) ::
      (< :: b :: HNil) ::
      (< :: f :: HNil) ::
      (< :: i :: HNil) ::
      (< :: S :: HNil) ::
      (< :: U :: HNil) ::
      (| :: b :: HNil) ::
      (| :: f :: HNil) ::
      (| :: i :: HNil) ::
      (| :: S :: HNil) ::
      (| :: U :: HNil) ::
      HNil
    )
  }
}

