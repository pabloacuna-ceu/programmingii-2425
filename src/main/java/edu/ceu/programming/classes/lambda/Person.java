package edu.ceu.programming.classes.lambda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Person {
    String name;
    Date birth;
    Person (String name, Date _birth){
        this.name = name;
        birth = _birth;
    }
    Person (String _name, String _birth){
        name = _name;
        try{
            birth = new SimpleDateFormat("dd/MM/yyyy").parse(_birth);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
    }
    public String getName() {
        return name;
    }
    public String getBirth() {
        return birth.toString();
    }

    public int age(){
        Calendar actualDate =Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(birth);
        int years = actualDate.get(Calendar.YEAR)- birthDate.get(Calendar.YEAR);
        int months =actualDate.get(Calendar.MONTH)- birthDate.get(Calendar.MONTH);
        int days = actualDate.get(Calendar.DATE)- birthDate.get(Calendar.DATE);
        if(months<0 || (months==0 && days<0)){
            years--; // years are adapted
        }
        return years;
    }

    public boolean retired() {
        return age()>67;
    }
    public String toString(){
        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
        String S="Name: "+name+"\n Birth date: "+SDF.format(birth)+
                "\n Age: "+ age()+"\n is Retired?: "+retired();
        return S;
    }



}
