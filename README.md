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
Please note, though, that the package is `com.farthergate.ctrlcurve`, not `com.github.aleksrutins.ctrlcurve`.

## Usage
```java
import com.farthergate.ctrlcurve.PID;

// ...

// Initialize constants
// The starting value
double initial = 0.0;
// The target value
double target = 10;
// The PID coefficient
double kp = 100.0;
// Time of integration
double ti = 50;
// Time of differentiation
double td = 50;
// Milliseconds to wait between iterations
double dt = 10;
// The tolerance for floating-point comparisons
double tolerance = 0.01;
// This is the current value; in a real-life situation, this would be written to actuators and read back from sensors.
double current = initial;

// Run a PID loop
// The loop ends once it is within `tolerance` of the target position. The value at that point is not necessarily the exact same value as the target position.
for(var pid = new PID(kp, ti, td, dt, tolerance, initial, target); pid.shouldContinue(); pid.update(current)) {

    // `correction()` does the actual PID math, and returns a correction value to smoothly transition between `target` and `current`.
    current += pid.correction();
}
```