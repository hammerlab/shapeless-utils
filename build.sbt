lazy val `shapeless-utils` =
  cross
    .in(file("."))
    .settings(
      v"1.5.1",
      dep(
        cats,
        shapeless
      )
    )
lazy val `shapeless-utils-root` =
  `shapeless-utils`
    .x
    .settings(
      github.repo("shapeless-utils")
    )
