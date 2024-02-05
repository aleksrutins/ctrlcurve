package com.farthergate.ctrlcurve;
import java.util.ArrayList;
import java.util.List;

import com.farthergate.ctrlcurve.math.Calculus;

public class PIDScope {
    boolean stopRequested = false;
    double kp, ki, kd;
    double error;
    double tolerance;
    double initial;
    double target;
    double current;
    int t = 0;
    private List<Double> history = new ArrayList<>();

    public PIDScope(double kp, double ki, double kd, double error, double tolerance, double initial, double target, double current) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
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
        if (!(kp == 0.0 && ki == 0.0 && kd == 0.0) && (target-initial) != 0.0) {
            return (
                (kp * (error / (target - initial))) // proportional
            //  + (ki * history.stream().reduce(0.0, Double::sum)) // integral
            //   + (kd * Calculus.differentiate(this::error, t)) // derivative
            );
        }
        return target;
    }
}
