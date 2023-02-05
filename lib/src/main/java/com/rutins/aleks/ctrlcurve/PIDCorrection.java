package com.rutins.aleks.ctrlcurve;

interface PIDCorrection {
    double apply(PIDScope scope, double initial, double current, double target, double correction);
}
