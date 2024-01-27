package com.farthergate.ctrlcurve;

public interface PIDCorrection {
    public double apply(PIDScope scope, double initial, double current, double target, double correction);
}
