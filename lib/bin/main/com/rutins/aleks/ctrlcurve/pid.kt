package com.rutins.aleks.ctrlcurve

typealias PIDCorrection = PIDScope.(initial: Double, current: Double, target: Double, correction: Double) -> Double

class PIDScope(val k: Double, var error: Double, val tolerance: Double, val initial: Double, val target: Double) {
    var stopRequested = false
    fun calculateCorrection(): Double {
        if (k != 0.0 && (target-initial) != 0.0) {
            return k * (error / (target - initial))
        }
        return target
    }
}

fun runPID(initial: Double, target: Double, tolerance: Double, k: Double, correction: PIDCorrection): Double {
    var current = initial
    var scope = PIDScope(k, target - current, tolerance, initial, target)
    while(!isWithinTolerance(current, target, tolerance) && !scope.stopRequested) {
        scope.error = target - current
        current = scope.correction(initial, current, target, target - current)
    }
    return current
}