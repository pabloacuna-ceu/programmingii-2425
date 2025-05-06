package edu.ceu.programming.exercises.datastructures;

import java.util.ArrayList;
import java.util.Arrays;

public class BasicExercises {

    public static void exercise1() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("Hugo");
        names.add("Paco");
        names.add("Luis");
        names.add("Donald");
        names.add("Daisy");

        names.stream().forEach(System.out::println);

    }

    public static double exercise2(ArrayList<Integer> numbers) {
        int sum = numbers.stream().reduce(0, Integer::sum);
        return numbers.isEmpty() ? 0d : (double)sum/numbers.size();
    }

    public static ArrayList<Integer> exercise3(ArrayList<Integer> numbers) {
        System.out.println(numbers);
        ArrayList<Integer> newNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            if (number % 2 != 0) {
                newNumbers.add(number);
            }
        }
        return newNumbers;

     }



    public static void main(String [] args) throws Exception {
        BasicExercises.exercise1();
        BasicExercises.exercise2(new ArrayList<Integer>(Arrays.asList(new Integer[] {1, 2, 3, 4, 5})));
        BasicExercises.exercise3(new ArrayList<Integer>(Arrays.asList(new Integer[] {12, 43, 2, 4, 1, 656, 4, 2})));

    }



}
