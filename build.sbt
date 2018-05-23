lazy val `shapeless-utils` =
  crossProject
    .in(file("."))
    .settings(
      v"1.3.0",
      sonatypeStage(1457),  // pick up staged org.hammerlab.test:base:1.0.1
      dep(shapeless)
    )
lazy val jvm = `shapeless-utils`.jvm
lazy val js  = `shapeless-utils`.js

lazy val root = rootProject(js, jvm)
