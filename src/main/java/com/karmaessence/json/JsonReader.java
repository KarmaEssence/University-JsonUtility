package com.karmaessence.json;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {

    private final String filePath;
    private BufferedReader flux;

    public JsonReader(String filePath)
    {
        this.filePath = filePath;

        try{
            InputStream currentlyReading = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(currentlyReading);
            flux = new BufferedReader(inputStreamReader);

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public String getFilePath()
    {
        return filePath;
    }


    public static JsonReader createReaderInstance(String filePath)
    {
        if(!Resources.checkIfFileExist(filePath))
            return null;
        return new JsonReader(filePath);
    }

    public Map<String,Object> getContent()
    {
        Map<String,Object> jsonArray = new HashMap<>();

        try{

        }catch(Exception e){
            e.printStackTrace();

        }
        return jsonArray;
    }

    public void close()
    {
        try{
            flux.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}

