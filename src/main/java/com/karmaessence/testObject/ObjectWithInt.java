package com.karmaessence.testObject;

public class ObjectWithInt {
    public Integer test;
    public Integer test1;

    public ObjectWithInt(int first, int second){
        test = first;
        test1 = second;
    }

    public ObjectWithInt(){
        this(10,20);
    }

   public void toPrint(){
       System.out.println("test : " + test + "test1 : " + test1);
   }

   public String toString(){
        return "test : " + test + " et test1 : " + test1;
   }
}
