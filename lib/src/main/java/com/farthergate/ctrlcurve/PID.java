package com.farthergate.ctrlcurve;
import com.farthergate.ctrlcurve.math.Tolerance;

public interface PID {
    public static double runPID(double initial, double target, double tolerance, double kp, double ki, double kd, PIDCorrection correction) {
        double current = initial;
        double dt = 100;
        PIDScope scope = new PIDScope(kp, ki, kd, target - current, tolerance, initial, target, current, dt);
        while(!Tolerance.isWithinTolerance(current, target, tolerance) && !scope.stopRequested) {
            scope.current = current;
            scope.error = target - current;
            current = correction.apply(scope, initial, current, target, target - current);
        }
        return current;
    }
}
