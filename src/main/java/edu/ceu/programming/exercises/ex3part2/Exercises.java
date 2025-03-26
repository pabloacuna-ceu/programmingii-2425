package edu.ceu.programming.exercises.ex3part2;

import java.util.stream.IntStream;

public class Exercises {

    public static void exercise1(int n) {
        IntStream stream = IntStream.rangeClosed(1, n);

        System.out.println(stream.reduce(0, (a, b) -> a + b));
    }

    public static void exercise2(int n) {
        IntStream stream = IntStream.rangeClosed(1, n);

        System.out.println(stream.reduce(1, (a, b) -> a * b));
    }

    public static void exercise3(int base, int exponent) {

        int result = IntStream.generate(() -> base)
                .limit(exponent) // Repeat `base` `n` times
                .reduce(1, (acc, x) -> acc * x);

        System.out.println(base + "^" + exponent + " = " + result); // Output: 3^4 = 81
    }

    public static void mapExample() {
        IntStream stream = IntStream.rangeClosed(1, 10);
        stream.map(n -> n*n).forEach(System.out::println);
    }

    public static void main(String [] args) {
        Exercises.exercise1(9);
        Exercises.exercise2(9);
        Exercises.exercise3(3, 4);

        Exercises.mapExample();



    }

}
