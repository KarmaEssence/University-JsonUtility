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
            String line;
            while ((line=flux.readLine())!=null){
                parseLine(jsonArray, line);
            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return jsonArray;
    }

    public void parseLine(Map<String,Object> map,String line){
        if(line.contains("{") || line.contains("}")
        || line.contains("[") || line.contains("]")
        || line.isEmpty())return;
        line = line.trim();
        String[] item = line.split(":");
        item[0] = item[0].replace("\"","");
        item[1] = item[1].replace(" ","");
        item[1] = item[1].replace(",","");
        map.put(item[0],item[1]);

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

