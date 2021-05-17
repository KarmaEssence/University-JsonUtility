package com.karmaessence.testObject;

import com.karmaessence.json.annotation.JsonSerializable;

@JsonSerializable
public class ObjectIntoOtherObject {

    @JsonSerializable
    public ObjectWithInt objInt;

    @JsonSerializable
    public Integer test2;

    public ObjectIntoOtherObject(){
        objInt = new ObjectWithInt();
        test2 = 10;
    }

    public String toString(){
        return objInt.toString() + " et test2 : " + test2;
    }
}
