package edu.ceu.programming.classes.datastructures;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayDeQueueExample {
    
    public static void main(String []args) {
        Deque<String> d = new ArrayDeque<String>();

        d.add("The");
        d.addFirst("To");
        d.addLast("Geeks");

        d.offer("For");
        d.offerFirst("Welcome");
        d.offerLast("Geeks");

        System.out.println("ArrayDeque: " + d);

    }

}
