package com.farthergate.ctrlcurve;

/**
 * A container for a PID loop.
 * 
 * This should be used by instantiating it in a for loop, with {@link shouldContinue} (and any user emergency stop conditions) as the condition and {@link update} as the increment, like so:
 * 
 * <pre>
 *{@code
 *for(var pid = new PID(...); pid.shouldContinue() && !isEmergencyStop(); pid.update(readSensorValues())) {
 *    writeActuators(pid.correction());
 *}
 *}
 * </pre>
 */
public class PID {
    double kp, ti, td, dt
         , t = 0
         , error
         , tolerance
         , initial
         , target
         , current
         , previousError
         , integralValue = 0;

    /**
     * Create a new PID calculator. The simplified PID algorithm is used, so the proportional coefficient stands in
     * for all coefficients - see {@link integral} and {@link derivative} for more details.
     * @param kp the PID (proportional) coefficient.
     * @param ti the time of integration. This is the number of iterations in which the integral term should attemt to correct to the target value.
     * @param td the time of differentiation. This is the number of iterations in which the derivative term should attemt to correct to the target value.
     * @param dt the number of milliseconds between each iteration.
     * @param tolerance the tolerance to allow between the final output and the target value.
     * @param initial the initial value.
     * @param target the target value.
     */
    public PID(double kp, double ti, double td, double dt, double tolerance, double initial, double target) {
        this.kp = kp;
        this.ti = ti;
        this.td = td;
        this.dt = dt;
        this.tolerance = tolerance;
        this.initial = initial;
        this.target = target;
        current = initial;
        error = target - current;
        previousError = target - current;
    }

    /**
     * Check if the current value is within tolerance of the target.
     * @return whether or not the PID loop should end.
     */
    public boolean shouldContinue() {
        return (target - current) > tolerance;
    }

    /**
     * Calculates the proportional component of the PID algorithm.
     * @return the proportional term.
     */
    public double proportional() {
        return kp * error;
    }

    /**
     * Calculates the integral component of the PID algorithm. This uses the simplified form of the PID equation, so instead of a separate
     * integral coefficient, the proportional coefficient ({@link kp}) divided by the time of integration ({@link ti}) is used.
     * @return the integral term.
     */
    public double integral() {
        return kp * integralValue/ti;
    }

    /**
     * Calculates the derivative component of the PID algorithm. This uses the simplified form of the PID equation, so instead of a separate
     * derivative coefficient, the proportional coefficient ({@link kp}) divided by the time of differentiation ({@link td}) would be used; however,
     * since {@link dt} is in milliseconds, the product is used instead. See
     * <a href="https://en.wikipedia.org/wiki/Proportional%E2%80%93integral%E2%80%93derivative_controller#cite_note-41">this Wikipedia footnote</a> for more details.
     * @return the derivative term.
     */
    public double derivative() {
        return kp * td * ((error - previousError) / dt) / 1000;
    }

    /**
     * Calculates the total PID correction to be sent to actuators.
     * @return The sum of the proportional, integral, and derivative components.
     */
    public double correction() {
        return proportional() + integral() + derivative();
    }

    /**
     * Wait {@link dt} milliseconds, and update the current, error, and integral values. This <b>MUST</b> be called at the end of each iteration,
     * or the PID loop will not work.
     * @param current the new current value, generally read from a sensor after the correction is applied.
     * @throws InterruptedException if {@link java.lang.Thread#sleep(long)} fails.
     */
    public void update(double current) throws InterruptedException {
        Thread.sleep((long)dt);
        t += dt;
        this.current = current;
        previousError = error;
        integralValue += error*dt;
        error = target - current;
    }
}
