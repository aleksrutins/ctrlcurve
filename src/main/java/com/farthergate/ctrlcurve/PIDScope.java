package com.farthergate.ctrlcurve;
import java.util.ArrayList;
import java.util.List;

import com.farthergate.ctrlcurve.math.Calculus;

public class PIDScope {
    boolean stopRequested = false;
    double kp, ti, td;
    double error;
    double tolerance;
    double initial;
    double target;
    double current;
    int t = 0;
    private List<Double> history = new ArrayList<>();

    public PIDScope(double kp, double ti, double td, double error, double tolerance, double initial, double target, double current) {
        this.kp = kp;
        this.ti = ti;
        this.td = td;
        this.error = error;
        this.tolerance = tolerance;
        this.initial = initial;
        this.target = target;
        update(current);
    }

    void update(double current) {
        t++;
        this.current = current;
        history.add(current);
    }

    private double smoothedHistory(double t) {
        double lower = 0, upper;
        boolean returnUpper = false;
        try {
            lower = history.get((int)Math.floor(t));
        } catch(IndexOutOfBoundsException e) {
            returnUpper = true;
        }
        try {
            upper = history.get((int)Math.ceil(t));
            if(returnUpper) return upper;
            double proportion = t % 1;
            return (lower * proportion) + (upper * (1 - proportion));
        } catch(IndexOutOfBoundsException e) {
            return lower;
        }
    }

    private double error(double t) {
        return target - smoothedHistory(t);
    }

    public double calculateCorrection() {
        var proportional = (error / (target - initial));
        var integral = (ti / Calculus.integrate(0.0, t, this::smoothedHistory) );
        var derivative = (td * Calculus.differentiate(this::error, t));
        System.out.println(String.format("%f,%f,%f", proportional, integral, derivative));
        if (kp != 0.0 && (target-initial) != 0.0) {
            return kp * (
                proportional
              + integral
              + derivative
            );
        }
        return target;
    }
}
