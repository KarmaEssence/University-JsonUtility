package com.karmaessence.launcher;

import com.karmaessence.json.JsonReader;
import com.karmaessence.json.JsonWriter;
import com.karmaessence.json.Resources;

import java.util.Map;

public class Launcher {

    /**
     * To start the program.
     * @param args Useless for the moment.
     */
    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        String s = Resources.getPathOfDataDirectory();
        JsonWriter jw = new JsonWriter("data/test");
        jw.fillJsonMap("leo",1);
        jw.fillJsonMap("tom","patate");
        jw.fillJsonMap("kevin",true);
        jw.fillJsonMap("Aya",'c');
        jw.save();
        JsonReader jr = JsonReader.createReaderInstance("data/test.json");
        assert jr != null;
        Map<String,Object> map = jr.getContent();
        //System.out.println(map.toString());

    }
}
