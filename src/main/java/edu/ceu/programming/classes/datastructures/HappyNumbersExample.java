package edu.ceu.programming.classes.datastructures;

import java.util.*;

public class HappyNumbersExample {
    public static boolean isHappy(int number) {
        HashSet<Integer> visited = new HashSet<>();
        int sum = 0, digit;
        System.out.print(number);
        while (number != 1 && !visited.contains(number)) {
            visited.add(number);
            sum = 0;
            while (number > 0) {
                digit = number % 10;
                sum += digit * digit;
                number /= 10;
            }
            number = sum;
            System.out.print("-->" + number);
        }
        return number == 1;
    }

    public static void main(String[] args) {
        int input = 45;
        System.out.println(" The number " + input + " is happy: " + isHappy(input));
        input = 10;
        System.out.println(" The number " + input + " is happy: " + isHappy(input));
    }
}
