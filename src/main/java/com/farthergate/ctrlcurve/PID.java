package com.farthergate.ctrlcurve;
import com.farthergate.ctrlcurve.math.Tolerance;

public interface PID {
    public static double runPID(double initial, double target, double tolerance, double kp, double ti, double td, PIDCorrection correction) {
        double current = initial;
        PIDScope scope = new PIDScope(kp, ti, td, target - current, tolerance, initial, target, current);
        while(!Tolerance.isWithinTolerance(current, target, tolerance) && !scope.stopRequested) {
            scope.current = current;
            scope.error = target - current;
            current = correction.apply(scope, initial, current, target, target - current);
            scope.update(current);
        }
        return current;
    }
}
