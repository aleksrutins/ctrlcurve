package com.rutins.aleks.ctrlcurve.math;

import java.util.function.Function;

public interface Calculus {
    /** Integrate fn between start and end using a right Riemann sum. */
    public static double integrate(double start, double end, double divisionSize, Function<Double, Double> fn) {
        double total = 0;
        for(double i = start + divisionSize; i <= end; i += divisionSize) {
            total += fn.apply(i) * divisionSize;
        }
        return total;
    }

    /** Integrate fn between start and end using a right Riemann sum with a division size of 0.001. */
    public static double integrate(double start, double end, Function<Double, Double> fn) {
        return integrate(start, end, 0.001, fn);
    }
}
