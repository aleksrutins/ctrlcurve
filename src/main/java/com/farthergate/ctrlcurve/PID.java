package com.farthergate.ctrlcurve;

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

    public boolean shouldContinue() {
        return (target - current) > tolerance;
    }

    public double proportional() {
        return kp * error;
    }

    public double integral() {
        return kp * integralValue/ti;
    }

    public double derivative() {
        return td * ((error - previousError) / dt) / 1000;
    }

    public double correction() {
        return proportional() + integral() + derivative();
    }

    public void update(double current) throws InterruptedException {
        Thread.sleep((long)dt);
        t += dt;
        this.current = current;
        previousError = error;
        integralValue += error*dt;
        error = target - current;
    }
}
