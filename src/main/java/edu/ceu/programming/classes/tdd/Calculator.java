package edu.ceu.programming.classes.tdd;

public class Calculator {

    public int add(int a, int b) {
        return a+b;
    }

    public int multiply(int a, int b) {
        return a*b;
    }

    public int substract(int a, int b) {
        return a-b;
    }

    public double divide(int a, int b) throws IllegalArgumentException {
        if (b == 0) {
            throw new IllegalArgumentException("b cannot be 0");
        } else {
            return a/b;
        }
    }

    public boolean isPositive(int a) {
        return a >= 0;
    }


    
}
