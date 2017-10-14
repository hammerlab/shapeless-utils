# shapeless-utils
shapeless-style type-classes for structural manipulation of algebraic data types

[![Build Status](https://travis-ci.org/hammerlab/shapeless-utils.svg?branch=master)](https://travis-ci.org/hammerlab/shapeless-utils)
[![Coverage Status](https://coveralls.io/repos/github/hammerlab/shapeless-utils/badge.svg?branch=master)](https://coveralls.io/github/hammerlab/shapeless-utils?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/org.hammerlab/shapeless-utils_2.11.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Corg.hammerlab%20shapeless-utils)

- `Find`: recursively find fields by type or name
  - [`hlist.Find`](src/main/scala/org/hammerlab/shapeless/hlist/Find.scala): recursively find field by type
  - [`record.Find`](src/main/scala/org/hammerlab/shapeless/record/Find.scala): recursively find field by name
  - [`record.Field`](src/main/scala/org/hammerlab/shapeless/record/Field.scala): recursively find field by name and type
- [`Flatten`](src/main/scala/org/hammerlab/shapeless/hlist/Flatten.scala): recursively flatten an `HList` or `case class` into an `HList`
- [`Select`](src/main/scala/org/hammerlab/shapeless/hlist/Select.scala): covariant version of [`shapeless.ops.hlists.Selector`](https://github.com/milessabin/shapeless/blob/shapeless-2.3.2/core/src/main/scala/shapeless/ops/hlists.scala#L842-L865)

## Examples

Setup, from [test//Utils.scala](src/test/scala/org/hammerlab/shapeless/Utils.scala):

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

Import syntax:

```scala
import org.hammerlab.shapeless._
```

### `findt`: find field by type

Adapted from [hlist.FindTest](src/test/scala/org/hammerlab/shapeless/hlist/FindTest.scala):

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

### `find`: find field by name

Adapted from [record.FindTest](src/test/scala/org/hammerlab/shapeless/record/FindTest.scala):

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

### `field`: find field by name and type

```scala
e.field[Boolean]('b)  // true
e.field[B]('b)        // B("abc")
```
