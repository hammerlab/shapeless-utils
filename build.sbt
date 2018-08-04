lazy val `shapeless-utils` =
  crossProject
    .in(file("."))
    .settings(
      v"1.5.0",
      dep(shapeless)
    )
lazy val jvm = `shapeless-utils`.jvm
lazy val js  = `shapeless-utils`.js
lazy val `shapeless-utils-root` =
  root(
    js,
    jvm
  )
  .settings(
    github.repo("shapeless-utils")
  )

default(
  hammerlab.test.suite.version := "1.0.2".snapshot,
  hammerlab.test. base.version := "1.0.2".snapshot
)
