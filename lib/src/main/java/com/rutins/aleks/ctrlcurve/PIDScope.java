package com.rutins.aleks.ctrlcurve;
import com.rutins.aleks.ctrlcurve.math.Calculus;

class PIDScope {
    boolean stopRequested = false;
    double kp, ki, kd;
    double error;
    double tolerance;
    double initial;
    double target;
    double current;
    double t = 0;
    double dt;
    public PIDScope(double kp, double ki, double kd, double error, double tolerance, double initial, double target, double current, double dt) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.error = error;
        this.tolerance = tolerance;
        this.initial = initial;
        this.target = target;
        this.current = current;
        this.dt = dt;
    }
    public double calculateCorrection() {
        if (!(kp == 0.0 && ki == 0.0 && kd == 0.0) && (target-initial) != 0.0) {
            return (
                (kp * (error / (target - initial))) // proportional
            //   + (ki * Calculus.integrate(initial, t, tau -> target - tau)) // integral
            );
        }
        return target;
    }
}