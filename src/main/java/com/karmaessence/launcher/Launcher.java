package com.karmaessence.launcher;

import com.karmaessence.json.JsonReader;
import com.karmaessence.json.JsonWriter;
import com.karmaessence.json.Resources;
import com.karmaessence.testObject.ObjectIntoOtherObject;
import com.karmaessence.testObject.ObjectWithInt;

import java.util.HashMap;
import java.util.Map;

public class Launcher {

    private static void testWriteAndReadObject(){
        String s = Resources.getPathOfDataDirectory();
        JsonWriter jw = new JsonWriter("data/test");

        jw.fillJsonMap("leo",1);
        jw.fillJsonMap("tom","patate");
        jw.fillJsonMap("kevin",true);
        jw.fillJsonMap("Aya",'c');
        jw.fillJsonMap("ObjectWithInt", new ObjectWithInt());
        jw.fillJsonMap("ObjOther", new ObjectIntoOtherObject());
        jw.save();
        JsonReader jr = JsonReader.createReaderInstance("data/test.json");
        assert jr != null;
        Map<String,Object> map = jr.getContent();
        System.out.println(map.toString());
        for(Object obj: map.keySet()){
            System.out.println(obj);
        }
        /*Object test = map.get("ObjectWithInt");
        System.out.println(test);*/

    }

    /**
     * To start the program.
     * @param args Useless for the moment.
     */
    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        testWriteAndReadObject();

        String c = "cddd";
        Map<String, Object> map = new HashMap<>();
        map.put("slssl", c);
        System.out.println(map);

    }
}
