package com.farthergate.ctrlcurve

/**
 * A container for a PID loop.
 *
 * This should be used by instantiating it in a for loop, with [shouldContinue] (and any user emergency stop conditions) as the condition and [update] as the increment, like so:
 *
 * <pre>
 * `for(var pid = new PID(...); pid.shouldContinue() && !isEmergencyStop(); pid.update(readSensorValues())) {
 * writeActuators(pid.correction());
 * }
` *
</pre> *
 */
class PID(var kp: Double, var ti: Double, var td: Double, var dt: Double, var tolerance: Double, var initial: Double, var target: Double) {
    var t: Double = 0.0
    var error: Double
    var current: Double
    var previousError: Double
    var integralValue: Double = 0.0

    /**
     * Create a new PID calculator. The simplified PID algorithm is used, so the proportional coefficient stands in
     * for all coefficients - see [integral] and [derivative] for more details.
     * @param kp the PID (proportional) coefficient.
     * @param ti the time of integration. This is the number of iterations in which the integral term should attempt to correct to the target value.
     * @param td the time of differentiation. This is the number of iterations in which the derivative term should attempt to correct to the target value.
     * @param dt the number of milliseconds between each iteration.
     * @param tolerance the tolerance to allow between the final output and the target value.
     * @param initial the initial value.
     * @param target the target value.
     */
    init {
        current = initial
        error = target - current
        previousError = target - current
    }

    /**
     * Check if the current value is within tolerance of the target.
     * @return whether the PID loop should end.
     */
    fun shouldContinue(): Boolean {
        return (target - current) > tolerance
    }

    /**
     * Calculates the proportional component of the PID algorithm.
     * @return the proportional term.
     */
    fun proportional(): Double {
        return kp * error
    }

    /**
     * Calculates the integral component of the PID algorithm. This uses the simplified form of the PID equation, so instead of a separate
     * integral coefficient, the proportional coefficient ([kp]) divided by the time of integration ([ti]) is used.
     * @return the integral term.
     */
    fun integral(): Double {
        return kp * integralValue / ti
    }

    /**
     * Calculates the derivative component of the PID algorithm. This uses the simplified form of the PID equation, so instead of a separate
     * derivative coefficient, the proportional coefficient ([kp]) divided by the time of differentiation ([td]) would be used; however,
     * since [dt] is in milliseconds, the product is used instead. See
     * [this Wikipedia footnote](https://en.wikipedia.org/wiki/Proportional%E2%80%93integral%E2%80%93derivative_controller#cite_note-41) for more details.
     * @return the derivative term.
     */
    fun derivative(): Double {
        return kp * td * ((error - previousError) / dt) / 1000
    }

    /**
     * Calculates the total PID correction to be sent to actuators.
     * @return The sum of the proportional, integral, and derivative components.
     */
    fun correction(): Double {
        return proportional() + integral() + derivative()
    }

    /**
     * Wait [dt] milliseconds.
     */
    fun sync() {
        Thread.sleep(dt.toLong())
    }

    /**
     * Update the current, error, and integral values. This **MUST** be called at the end of each iteration, **after** [sync],
     * or the PID loop will not work.
     * @param current the new current value, generally read from a sensor after the correction is applied.
     * @throws InterruptedException if [java.lang.Thread.sleep] fails.
     */
    @Throws(InterruptedException::class)
    fun update(current: Double) {
        t += dt
        this.current = current
        previousError = error
        integralValue += error * dt
        error = target - current
    }
}
