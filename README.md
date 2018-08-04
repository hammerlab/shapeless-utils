# shapeless-utils
shapeless-style type-classes for structural manipulation of algebraic data types

[![Build Status](https://travis-ci.org/hammerlab/shapeless-utils.svg?branch=master)](https://travis-ci.org/hammerlab/shapeless-utils)
[![codecov](https://codecov.io/gh/hammerlab/shapeless-utils/branch/master/graph/badge.svg)](https://codecov.io/gh/hammerlab/shapeless-utils)
[![org.hammerlab:shapeless-utils_2.1[12] on Maven Central](https://img.shields.io/maven-central/v/org.hammerlab/shapeless-utils_2.11.svg?maxAge=600&label=org.hammerlab:shapeless-utils_2[12])](http://search.maven.org/#search%7Cga%7C1%7Corg.hammerlab%20shapeless-utils)

Feature overview:

- `Find`: recursively find fields by type and/or name
  - [`hlist.Find`](shared/src/main/scala/org/hammerlab/shapeless/hlist/Find.scala): recursively find field by type
  - [`record.Find`](shared/src/main/scala/org/hammerlab/shapeless/record/Find.scala): recursively find field by name
  - [`record.Field`](shared/src/main/scala/org/hammerlab/shapeless/record/Field.scala): recursively find field by name and type
- [`Flatten`](shared/src/main/scala/org/hammerlab/shapeless/hlist/Flatten.scala): recursively flatten an `HList` or `case class` into an `HList`
- [`Select`](shared/src/main/scala/org/hammerlab/shapeless/hlist/Select.scala): covariant version of [`shapeless.ops.hlists.Selector`](https://github.com/milessabin/shapeless/blob/shapeless-2.3.2/core/src/main/scala/shapeless/ops/hlists.scala#L842-L865)
- [`Cast`](shared/src/main/scala/org/hammerlab/shapeless/coproduct/Cast.scala): evidence that a product – or all branches of a coproduct – matches a given HList structure
  - [`Singleton`](shared/src/main/scala/org/hammerlab/shapeless/coproduct/Singleton.scala): above when the HList contains one element
- [`TList`](shared/src/main/scala/org/hammerlab/shapeless/tlist/TList.scala): type-level list whose elements are all the same type
- [implicit instances of `shapeless.Nat` integer-types](shared/src/main/scala/org/hammerlab/shapeless/nat/implicits.scala)
- [`Unroll`](shared/src/main/scala/org/hammerlab/shapeless/nesting/Unroll.scala): count and unroll repeated applications of a type-constructor
- [`seq.Nested`](shared/src/main/scala/org/hammerlab/shapeless/nesting/seq/Nested.scala): count and convert layers of nested `Seq`s (or its subtypes) to (the same number of layers of) vanilla `Seq`s 
- [`Instances`](shared/src/main/scala/org/hammerlab/shapeless/instances/Instances.scala): summon all possible instances of an ADT type, if there are only finitely many
  - [`Singleton`](shared/src/main/scala/org/hammerlab/shapeless/instances/Singleton.scala): summon the single instance of a `case object`, by its type
- [`Options`](shared/src/main/scala/org/hammerlab/shapeless/coproduct/Options.scala): compute an `HList` comprised of all branches of a `Coproduct`
- [`Cartesian`](shared/src/main/scala/org/hammerlab/shapeless/hlist/Cartesian.scala): compute the Cartesian product of two `HList`s 

## Examples

Setup, from [test//Utils.scala](shared/src/test/scala/org/hammerlab/shapeless/Utils.scala):

```scala
case class A(n: Int)
case class B(s: String)
case class C(a: A, b: B)
case class D(b: Boolean)
case class E(c: C, d: D, a: A, a2: A)
case class F(e: E)

val a = A(123)
val b = B("abc")
val c = C(a, b)
val d = D(true)
val e = E(c, d, A(456), A(789))
val f = F(e)
```

Default for importing everything from this library, as well as upstream shapeless:

```scala
import hammerlab.shapeless._
import shapeless._
```

### `findt`

Find field by type

```scala
a.findt[Int]      // 123
b.findt[String]   // "abc"

c.findt[Int]      // 123
c.findt[String]   // "abc"

d.findt[Boolean]  // true

e.findt[B]        // b
e.findt[C]        // c
e.findt[D]        // d
e.findt[Boolean]  // true
e.findt[String]   // "abc"

e.findt[A]        // doesn't compile: E.a, E.a2, and E.c.a both match
e.findt[Int]      // doesn't compile: E.a.n, E.a2.n, and E.c.a.n both match
```

(adapted from [hlist.FindTest](shared/src/test/scala/org/hammerlab/shapeless/hlist/FindTest.scala))

### `find`

Find field by name:

```scala
a.find('n)   // 123

b.find('s)   // "abc"

c.find('n)   // 123
c.find('s)   // "abc"

d.find('b)   // true

e.find('b)   // B("abc")
e.find('c)   // C(A(123), B("abc"))
e.find('d)   // D(true)
e.find('s)   // "abc"
e.find('a2)  // A(789)

e.find('a)   // doesn't compile: E.c.a and E.a both match
e.find('n)   // doesn't compile: E.a.n, E.a2.n, and E.c.a.n both match
```

(adapted from [record.FindTest](shared/src/test/scala/org/hammerlab/shapeless/record/FindTest.scala))

### `field`

Find field by name and type:

```scala
e.field[Boolean]('b)  // true
e.field[B]('b)        // B("abc")
```

### `Singleton` / `.map`

Manipulate a generic hierarchy that always wraps one element of a given type.

Example hierarchy:

```scala
sealed trait Foo

case class A(n: Int) extends Foo

sealed trait Bar extends Foo
case class B(n: Int) extends Bar
case class C(n: Int) extends Bar
case class D(n: Int) extends Bar
```

Transform the enclosed `Int`, preserving concrete type:

```scala
implicit val singleton = Singleton[Foo, Int]

val foos = Seq[Foo](A(1), B(2), C(3), D(4))

foos.map(_.map(_ * 10))
// Seq(A(10), B(20), C(30), D(40))
```

#### `Cast`

Generalization of the above, providing evidence that a type is structured like a given `HList`.

Given a hierarchy with all leafs matching `Int :: String :: HNil`:

```scala
sealed trait X
case class Y(n: Int, s: String) extends X
case class Z(n: Int, s: String) extends X

val y = Y(111, "abc")
val z = Z(222, "def")
val xs = Seq[X](y, z)
```

Expose a `map` operation that transforms the HList representation, preserving the container type:

```scala
xs map {
  _ map {
	case n :: s :: ⊥ ⇒
	  2*n :: s.reverse :: ⊥
  }
}
// Seq(Y(222, cba), Z(444, fed))
```

#### `TList`

Lists whose elements are the same type, and whose length is a type-level integer:

```scala
// Left out of default wildcard-import to avoid conflicts between tlist.:: and shapeless.::
import hammerlab.shapeless.tlist._

1 :: 2 :: 3 :: TNil
// Int :: Int :: Int :: org.hammerlab.shapeless.tlist.TNil = ::(1,::(2,::(3,TNil)))
```

You can't construct a mixed-type TList:

```scala
1 :: 2 :: 'a :: TNil
// <console>:15: error: type mismatch;
//  found   : Int
//  required: Symbol
//        1 :: 2 :: 'a :: TNil
//               ^
```

Tuples can be automatically converted:

```scala
TList((1, 2, 3))
```


[`Zip`](shared/src/main/scala/org/hammerlab/shapeless/tlist/Zip.scala), [`Map`](shared/src/main/scala/org/hammerlab/shapeless/tlist/Map.scala), and [`ToList`](shared/src/main/scala/org/hammerlab/shapeless/tlist/ToList.scala) helpers are also available:

```scala
// Zip two equal-length TLists of Ints, sum them element-wise, and convert to a vanilla List
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
)
// 11 :: 22 :: Nil

elemSum(
   1 ::  2 :: TNil,
  10 :: 20 :: TNil
)
// 11 :: 22 :: Nil
```

#### Implicit instances of type-level integers

```scala
the[_1]
the[_2]
// etc.
```

#### `Unroll`

```scala
the[Unroll[Int, Seq]]                // output types: _0, Int
the[Unroll[Seq[Int], Seq]]           // output types: _1, Int
the[Unroll[Seq[Seq[Int]], Seq]]      // output types: _2, Int
the[Unroll[Seq[Seq[Seq[Int]]], Seq]] // output types: _3, Int

// Proof:
the[Aux[Int, Seq, _0, Int]]
the[Aux[Seq[Int], Seq, _1, Int]]
the[Aux[Seq[Seq[Int]], Seq, _2, Int]]
the[Aux[Seq[Seq[Seq[Int]]], Seq, _3, Int]]
```

#### `seq.Nested`

```scala
val converter = the[Nested[List[Vector[IndexedSeq[Int]]]]]
converter(
  List(
    Vector(
      IndexedSeq( 1,  2,  3),
      IndexedSeq( 4,  5,  6)
    ),
    Vector(
      IndexedSeq( 7,  8,  9),
      IndexedSeq(10, 11, 12)
    )
  )
)
// Seq(
//   Seq(
//     Seq( 1,  2,  3),
//     Seq( 4,  5,  6)
//   ),
//   Seq(
//     Seq( 7,  8,  9),
//     Seq(10, 11, 12)
//   )
// )
```
