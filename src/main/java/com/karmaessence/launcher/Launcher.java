package com.karmaessence.launcher;

import com.karmaessence.json.JsonReader;
import com.karmaessence.json.JsonWriter;
import com.karmaessence.json.Resources;
import com.karmaessence.testObject.ObjectIntoOtherObject;
import com.karmaessence.testObject.ObjectWithInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Launcher {

    private static void testWriteAndReadObject(){
        String s = Resources.getPathOfDataDirectory();
        JsonWriter jw = new JsonWriter("data/test");

        /*jw.fillJsonMap("leo",1);
        jw.fillJsonMap("tom","patate");
        jw.fillJsonMap("kevin",true);
        jw.fillJsonMap("Aya",'c');
        jw.fillJsonMap("ObjectWithInt", new ObjectWithInt());
        jw.fillJsonMap("ObjOther", new ObjectIntoOtherObject());*/

        /*List<Integer> intList = new ArrayList<>();
        intList.add(10);
        intList.add(11);
        List<ObjectWithInt> objectList = new ArrayList<>();
        objectList.add(new ObjectWithInt());
        objectList.add(new ObjectWithInt());*/
        /*jw.fillJsonMap("intList", intList);
        jw.fillJsonMap("objectList", objectList);*/

        /*List<List<Integer>> listListInt = new ArrayList<>();
        ArrayList<Integer> intListTest = new ArrayList<>();
        ArrayList<Integer> intListTest2 = new ArrayList<>();
        intListTest.add(25);
        intListTest.add(50);
        intListTest2.add(30);
        intListTest2.add(90);
        listListInt.add(intListTest);
        listListInt.add(intListTest2);*/
        //jw.fillJsonMap("listListInt", listListInt);

        /*ArrayList<Object> intListObject = new ArrayList<>();
        intListObject.add("char");
        intListObject.add(70);*/
        //jw.fillJsonMap("intListObject", intListObject);

        ObjectWithInt objInt = new ObjectWithInt();
        jw.serializeObject(objInt);

        //jw.saveMap();
        JsonReader jr = JsonReader.createReaderInstance("data/test.json");
        assert jr != null;
        Map<String,Object> map = jr.getContent();
        System.out.println(map.toString());
    }

    public static void testSerializeAndDeserialize(){
        JsonWriter jw = new JsonWriter("data/test");
        JsonReader jr = JsonReader.createReaderInstance("data/test.json");
        ObjectWithInt objInt = new ObjectWithInt(70, 140);
        System.out.println(objInt.toString());
        jw.serializeObject(objInt);
        assert jr != null;
        objInt = (ObjectWithInt) jr.deserialize();
        System.out.println(objInt.toString());
    }

    /**
     * To start the program.
     * @param args Useless for the moment.
     */
    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        //testWriteAndReadObject();
        testSerializeAndDeserialize();
    }
}
