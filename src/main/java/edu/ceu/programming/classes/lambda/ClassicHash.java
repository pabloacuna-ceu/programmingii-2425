package edu.ceu.programming.classes.lambda;

public class ClassicHash implements Hashing {
    @Override
    public int hash(String s) {
        int sum = 0;
        for (char c : s.toCharArray())
            sum += c;
        return sum%DIM;
    }
}
