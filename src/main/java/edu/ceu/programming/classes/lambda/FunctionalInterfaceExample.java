package edu.ceu.programming.classes.lambda;

@FunctionalInterface
interface MyFunction {
    int apply(int x);
}

public class FunctionalInterfaceExample {

    public static void main(String [] args) {
        MyFunction doubleValue = x -> x*2;
        System.out.println(doubleValue.apply(5));
    }

}
