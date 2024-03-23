package com.farthergate.ctrlcurve;
import com.farthergate.ctrlcurve.math.Tolerance;

public class PID {
    double kp, ti, td, dt
         , t = 0
         , error
         , tolerance
         , initial
         , target
         , current
         , previousError = 0
         , integral = 0;

    public PID(double kp, double ti, double td, double dt, double tolerance, double initial, double target) {
        this.kp = kp;
        this.ti = ti;
        this.td = td;
        this.dt = dt;
        this.tolerance = tolerance;
        this.initial = initial;
        this.target = target;
        current = initial;
    }
    
    public boolean shouldContinue() {
        return !Tolerance.isWithinTolerance(target, current, tolerance);
    }

    public double correction() {
        var error = target - current;
        var proportional = error;
        integral = integral + error*dt;
        var derivative = (error - previousError) / dt;
        previousError = error;
        return kp * (proportional + integral/ti + td*derivative/1000);
    }

    public void sync() throws InterruptedException {
        Thread.sleep((long)dt);
        t += dt;
    }
}
