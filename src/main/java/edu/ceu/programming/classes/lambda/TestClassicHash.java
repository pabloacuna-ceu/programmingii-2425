package edu.ceu.programming.classes.lambda;

import java.util.Arrays;
import java.util.List;

public class TestClassicHash {
    public static String[] insert (List<String> l, Hashing h) {
        String [] a = new String[Hashing.DIM];
        l.forEach(e -> {
            a[h.hash(e)] = e;
        });
        return a;
    }

    public static void main(String [] args) {
        // Normal use
        ClassicHash h = new ClassicHash();
        System.out.println("Classic Hash (normal use) " + h.hash("test"));

        // Using nested classes
        System.out.println("Hash through nested classes: " + (new Hashing(){
            public int hash(String s) {
                int sum = 0, i = 1;
                for (char c : s.toCharArray())
                    sum += c * i++;
                return sum%Hashing.DIM;
            }
        }).hash("test"));

        // using Lambda Expressions
        // s.chars() converts the string into a flow of integers
        Hashing h2 = (String s) -> s.chars().reduce(0, (a, b) -> a*10 + b) % Hashing.DIM;
        System.out.println("Hash using Lambda expressions: " + h2.hash("test"));

        // As method parameters
        Hashing h3 = (String s) -> (s.chars().reduce(0, (a, b) -> a*10 + b)) % Hashing.DIM;
        List<String> myList = Arrays.asList("personA", "personB", "personC");
        String [] v = insert(myList, h3);
        System.out.println("As method parameters: " + v[h3.hash("personA")]);


    }
}
