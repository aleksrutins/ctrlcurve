package com.farthergate.ctrlcurve;

interface PIDCorrection {
    double apply(PIDScope scope, double initial, double current, double target, double correction);
}
