package edu.ceu.programming.classes.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TestStandardFI {

    public static String[] insert (List<String> l, Function<String,Integer> h){
        String[] a=new String[100];
        l.forEach(e->{a[h.apply(e)]=e;});
        return a;
    }
    public static void main(String[] args) {

        List<String> myList = Arrays.asList("personA", "personB", "personC");
        Function<String,Integer> f=(String s) ->
                (s.chars().reduce(0,(a,b)->  a*10 + b) % 100);// hash function
        String [] v= insert(myList,f);
        System.out.print("Output using standard functional interfaces:"+v[f.apply("personA")]);
    }



}
