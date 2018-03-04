lazy val shapeless_utils = crossProject.in(file(".")).settings(
  name := "shapeless-utils",
  v"1.2.0",
  dep(shapeless)
)
lazy val jvm = shapeless_utils.jvm
lazy val js  = shapeless_utils.js

lazy val root = rootProject(js, jvm)
