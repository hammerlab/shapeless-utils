# shapeless-utils
shapeless-style type-classes for structural manipulation of algebraic data types

[![Build Status](https://travis-ci.org/hammerlab/shapeless-utils.svg?branch=master)](https://travis-ci.org/hammerlab/shapeless-utils)
[![codecov](https://codecov.io/gh/hammerlab/shapeless-utils/branch/master/graph/badge.svg)](https://codecov.io/gh/hammerlab/shapeless-utils)
[![org.hammerlab:shapeless-utils_2.1[12] on Maven Central](https://img.shields.io/maven-central/v/org.hammerlab/shapeless-utils_2.11.svg?maxAge=600&label=org.hammerlab:shapeless-utils_2[12])](http://search.maven.org/#search%7Cga%7C1%7Corg.hammerlab%20shapeless-utils)

- `Find`: recursively find fields by type and/or name
  - [`hlist.Find`](shared/src/main/scala/org/hammerlab/shapeless/hlist/Find.scala): recursively find field by type
  - [`record.Find`](shared/src/main/scala/org/hammerlab/shapeless/record/Find.scala): recursively find field by name
  - [`record.Field`](shared/src/main/scala/org/hammerlab/shapeless/record/Field.scala): recursively find field by name and type
- [`Flatten`](shared/src/main/scala/org/hammerlab/shapeless/hlist/Flatten.scala): recursively flatten an `HList` or `case class` into an `HList`
- [`Select`](shared/src/main/scala/org/hammerlab/shapeless/hlist/Select.scala): covariant version of [`shapeless.ops.hlists.Selector`](https://github.com/milessabin/shapeless/blob/shapeless-2.3.2/core/src/main/scala/shapeless/ops/hlists.scala#L842-L865)
- [`Cast`](shared/src/main/scala/org/hammerlab/shapeless/coproduct/cast.scala): evidence that a product – or all branches of a coproduct – matches a given HList structure
  - [`Singleton`](shared/src/main/scala/org/hammerlab/shapeless/coproduct/singleton.scala): above when the HList contains one element
- [`TList`](shared/src/main/scala/org/hammerlab/shapeless/tlist/TList.scala): list whose elements are the same type, and whose length is a type-level integer
- [implicit instances of `shapeless.Nat` integer-types](shared/src/main/scala/org/hammerlab/shapeless/nat/implicits.scala)

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

Lists whose elements are the same type, and whose length is a type-level integer.

Constructible most readily from tuples:

```scala
import shapeless.nat._
(1, 2, 3): TList[Int, _3]
```

#### Implicit instances of type-level integers

```scala
the[_1]
the[_2]
// etc.
```
