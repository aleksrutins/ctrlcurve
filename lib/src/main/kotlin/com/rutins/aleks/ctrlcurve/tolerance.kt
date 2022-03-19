package com.rutins.aleks.ctrlcurve
import kotlin.math.abs

fun isWithinTolerance(a: Double, b: Double, tolerance: Double): Boolean {
    return abs(b - a) < tolerance
}