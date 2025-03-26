package edu.ceu.programming.exercises.ex3part1;

import java.util.stream.IntStream;

public class Exercise2 {

    public static void main(String[] args) {
        // Create an IntStream using the static 'of' method
        IntStream stream = IntStream.range(1, 6);

        System.out.println("Print each number (range) with Method Reference: ");
        // Process the stream: print each number
        stream.forEach(System.out::println);

        // Another way
        System.out.println("Print each number (range) in one line: ");
        IntStream.range(1, 6).forEach(System.out::println);

        // Range Closed
        IntStream streamClosed = IntStream.rangeClosed(1, 6);
        System.out.println("Print each number with Range Closed (both inclusive)");
        streamClosed.forEach(System.out::println);
    }


}
