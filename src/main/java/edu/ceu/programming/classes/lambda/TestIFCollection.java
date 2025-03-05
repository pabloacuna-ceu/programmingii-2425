package edu.ceu.programming.classes.lambda;

import java.util.ArrayList;

public class TestIFCollection {

    public static void main(String [] args){
        ArrayList<Person> list = new ArrayList();
        list.add(new Person("Pepe", "27/11/1958"));
        list.add(new Person("Andrés", "12/01/2005"));
        list.add(new Person("Juan", "01/07/2019"));
        list.add(new Person("Enrique", "23/11/1953"));
        list.sort( (l, r) -> l.name.compareTo(r.name));
        System.out.println("========================\nSorted by name");
        list.forEach(e->System.out.println(e));
        list.sort( (l, r) -> l.age()-r.age());
        System.out.println("========================\nSorted by age");
        list.forEach(e->System.out.println(e));
    }


}
