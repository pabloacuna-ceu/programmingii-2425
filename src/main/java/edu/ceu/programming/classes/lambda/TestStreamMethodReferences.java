package edu.ceu.programming.classes.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestStreamMethodReferences {

    public static void main(String [] args) {
        ArrayList<Person> list = new ArrayList();
        list.add(new Person("Pepe", "27/11/1958"));
        list.add(new Person("Andrés", "12/01/2005"));
        list.add(new Person("Juan", "01/07/2019"));
        list.add(new Person("Enrique", "23/11/1953"));
        List<String> retired = list
                .stream()
                .filter(Person::retired)
                .map(Person::getName)
                .collect(Collectors.toList());
        retired.forEach(System.out::println);
    }

}
