package com.rutins.aleks.ctrlcurve.math;

public interface Tolerance {
    public static boolean isWithinTolerance(double a, double b, double tolerance) {
        return Math.abs(b - a) < tolerance;
    }
}