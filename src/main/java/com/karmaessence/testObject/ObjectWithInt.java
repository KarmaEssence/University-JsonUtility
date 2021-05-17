package com.karmaessence.testObject;

import com.karmaessence.json.annotation.JsonSerializable;

public class ObjectWithInt {

    @JsonSerializable
    public Integer test;

    @JsonSerializable
    public Integer test1;

    public ObjectWithInt(int first, int second){
        test = first;
        test1 = second;
    }

    public ObjectWithInt(){
        this(10,5);
    }

   public void toPrint(){
       System.out.println("test : " + test + "test1 : " + test1);
   }

   public String toString(){
        return "test : " + test + " et test1 : " + test1;
   }
}
