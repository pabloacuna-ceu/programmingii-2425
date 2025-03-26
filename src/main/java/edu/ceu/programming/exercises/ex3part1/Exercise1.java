package edu.ceu.programming.exercises.ex3part1;

import java.util.stream.IntStream;

public class Exercise1 {

    public static void main(String[] args) {
        // Create an IntStream using the static 'of' method
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);

        // Process the stream: print each number
        stream.forEach(System.out::println);
    }


}
