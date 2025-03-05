package edu.ceu.programming.classes.lambda;

import java.util.function.*;

public class LambdaExpressions {

    public static void main(String [] args) {
        Consumer<String> print = s -> System.out.println(s);
        print.accept("Hello"); // Prints "Hello"

	    Supplier<Double> randomValue = () -> Math.random();
        System.out.println(randomValue.get()); // Prints a random value

        Function<Integer, Integer> square = x -> x * x;
	    System.out.println(square.apply(5)); // Prints 25

        BiFunction<Integer, Integer, Integer> sum = (x, y) -> x + y;
        System.out.println(sum.apply(3, 7)); // Prints 10

        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println(isEven.test(4)); // Prints true

        BiPredicate<String, Integer> isLongerThan = (str, len) -> str.length() > len;
        System.out.println(isLongerThan.test("Hello", 3)); // Prints true
    }
}
