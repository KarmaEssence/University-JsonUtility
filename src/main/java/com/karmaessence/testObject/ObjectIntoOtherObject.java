package com.karmaessence.testObject;

public class ObjectIntoOtherObject {
    public ObjectWithInt objInt;
    public Integer test2;

    public ObjectIntoOtherObject(){
        objInt = new ObjectWithInt();
        test2 = 10;
    }

    public String toString(){
        return objInt.toString() + " et test2 : " + test2;
    }
}
