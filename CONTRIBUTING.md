# Contributing to JVM Base Projects

### Code style

This project uses the [ktfmt](https://github.com/facebook/ktfmt) (a program that pretty-prints (formats) Kotlin
code, based on [google-java-format](https://github.com/google/google-java-format)), provided via
the [spotless](https://github.com/diffplug/spotless) gradle plugin, and the bundled project IntelliJ codestyle.

If you find that one of your pull reviews does not pass the CI server check due to a code style conflict, you can
easily fix it by running: `./gradlew spotlessApply`.

If you use IntelliJ, you can also use the run configuration called **Run Spotless**,
or there's also a [ktfmt IntelliJ plugin](https://plugins.jetbrains.com/plugin/14912-ktfmt) for the same purpose.
