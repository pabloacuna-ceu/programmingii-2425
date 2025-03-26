package edu.ceu.programming.exercises.ex3part1;

import java.util.stream.IntStream;

public class Exercise3 {

    public static void main(String[] args) {
        // iterate function start at the seed and defines what is the next number
        // iterate(initial_value, nextValue calculated)
        IntStream stream = IntStream.iterate(1, n -> n + 1).limit(10);
        // Print the numbers in the stream (one by one) using Method Reference
        stream.forEach(System.out::println);

        // You can define the limit in the same iterate function
        IntStream streamLimit = IntStream.iterate(1, n -> n <= 10, n -> n + 1);
        streamLimit.forEach(System.out::println);

    }


}
