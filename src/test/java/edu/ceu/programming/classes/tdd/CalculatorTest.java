package edu.ceu.programming.classes.tdd;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class CalculatorTest {
    
    Calculator calc = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5, calc.add(2, 3), "Sum is correct");
    }

    @Test
    public void testMultiply() {
        assertTrue(calc.multiply(2, 4) == 8, "Mult should be 8");
    }

    @Test
    public void testSubstract() {
        assertNotEquals(0, calc.substract(5, 2), "Result should not be zero");
    }

    @Test
    public void testDivide() {
        assertThrows(IllegalArgumentException.class, () -> calc.divide(15, 0));
    }

    @Test
    public void testIsPositive() {
        assertAll("Checking isPositive",
            () -> calc.isPositive(3),
            () -> calc.isPositive(-2),
            () -> calc.isPositive(0)
        );
    }

}
