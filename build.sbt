lazy val `shapeless-utils` =
  crossProject
    .in(file("."))
    .settings(
      v"1.3.0",
      dep(shapeless)
    )
default(
  sonatypeStage(1464)  // tests 1.0.1
)
lazy val jvm = `shapeless-utils`.jvm
lazy val js  = `shapeless-utils`.js
lazy val `shapeless-utils-root` = root(js, jvm)
