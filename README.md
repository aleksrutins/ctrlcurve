# ctrlcurve
_A library for PID (proportional-integral-derivative) control in Kotlin._

## Getting started
I learned the hard way with [station](https://github.com/aleksrutins/station) that GitHub Packages maybe isn't the best idea for Maven/Gradle packages. So, just use JitPack.
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

...

dependencies {
    implementation 'com.github.aleksrutins:ctrlcurve:0.1.0'
}
```
Please note, though, that the package is `com.rutins.aleks.ctrlcurve`, not `com.github.aleksrutins.ctrlcurve`.

## Usage
```kotlin
import com.rutins.aleks.ctrlcurve.runPID

// ...

// Initialize constants
// The starting value
val initial = 0.0
// The target value
val target = 3600.0
// The PID coefficient
val k = 100.0
// The tolerance for floating-point comparisons
val tolerance = 0.01

// Run a PID loop
// The final value is the value that the loop ends at once it is within `tolerance` of the target position. It is not necessarily the same value as the target position.
val finalValue = runPID(initial, target, tolerance, k) { initial, current, target, error
    // `initial` and `target` are the initial and target values, as passed in to `runPID`
    // `current` is the current value, as returned by the previous iteration of the loop
    // `error` is the difference between `target` and `current`

    // `calculateCorrection()` does the actual PID math, and returns a correction value to smoothly transition between `target` and `current`.
    val correction = calculateCorrection()

    // Return the new value
    current + correction
}