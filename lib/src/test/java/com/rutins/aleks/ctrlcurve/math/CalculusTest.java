package com.rutins.aleks.ctrlcurve.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculusTest {
    @Test
    void integralTest() {
        assertEquals(6.0, Calculus.integrate(0, 3, 1, x -> x));
    }
}
