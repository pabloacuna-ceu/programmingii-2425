package edu.ceu.programming.exercises.ex3part1;

import java.util.stream.IntStream;

public class Exercise5 {

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        return IntStream.rangeClosed(2, (int) Math.sqrt(num))
                .allMatch(n -> num % n != 0);
    }

    public static void main(String[] args) {
        // Generate numbers starting from 2 and filter only primes
        IntStream.iterate(2, n -> n + 1)
                .filter(Exercise5::isPrime)
                .limit(10)
                .forEach(System.out::println);
    }


}
