package com.karmaessence.launcher;

import com.karmaessence.json.JsonReader;
import com.karmaessence.json.JsonWriter;
import com.karmaessence.json.Resources;

public class Launcher {

    public static void main(String[] args){
        System.out.println(System.getProperty("user.dir"));
        String s = Resources.getPathOfDataDirectory();
        JsonWriter jw = new JsonWriter("data/test");
        jw.fillJsonMap("leo",1);
        jw.save();
    }
}
