package com.farthergate.ctrlcurve;

public interface PIDCorrection {
    public double apply(double initial, double current, double target, double correction);
}
