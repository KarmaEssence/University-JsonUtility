package com.karmaessence.json;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        Map<String,Object> jsonArray = new LinkedHashMap<>();

        try{
            String line;
            while ((line=flux.readLine())!=null){
                if(line.contains(":") && line.contains("{")){
                    System.out.println(line);
                    Object[] item = cleanValue(line);
                    jsonArray.put((String) item[0], makeSubObject((String)cleanValue(flux.readLine())[1]));
                }else{
                    parseLine(jsonArray, line);
                }
            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return jsonArray;
    }

    private Object makeSubObject(String classType) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, NoSuchFieldException {
        classType = classType.replace("\"", "");
        Class getClass = Class.forName(classType);
        Object res = getClass.getConstructor().newInstance();
        Field[] resFields = res.getClass().getFields();
        int currentIndex = 0;
        String line;
        while (!isEndOfObject((line=flux.readLine()))){
            System.out.println("Condition 1 :" + (line.contains(":") && line.contains("{")));
            System.out.println("Name : " + line);
            if(line.contains(":") && line.contains("{")){
                Object obj = makeSubObject((String)cleanValue(flux.readLine())[1]);
                resFields[currentIndex].setAccessible(true);
                resFields[currentIndex].set(res, obj);
            }else{
                Object[] item = cleanValue(line);
                resFields[currentIndex].setAccessible(true);
                System.out.println(item[1].getClass());
                resFields[currentIndex].set(res, item[1]);

            }
            currentIndex += 1;
        }
        return res;
    }

    public void parseLine(Map<String,Object> map,String line){
        if(line.contains("{") || line.contains("}")
        || line.contains("[") || line.contains("]")
        || line.isEmpty())return;
        Object[] item = cleanValue(line);
        map.put((String) item[0],item[1]);

    }

    private boolean isEndOfObject(String line){
        return line.contains("}") || line.contains("]");
    }

    private Object[] cleanValue(String line){
        line = line.trim();
        Object[] item = line.split(":");
        String key = (String) item[0];
        key = key.replace("\"","");
        String firstVal = (String) item[1];
        firstVal = firstVal.replace(" ","");
        firstVal = firstVal.replace(",","");
        firstVal = firstVal.replace("\"","");
        Object[] res = new Object[2];
        res[0] = key;
        if(((String) item[0]).contains("classType") || Utility.firstCharacterIsUpper(((String) item[0]).charAt(0))){
            res[1] = firstVal;
        }else{
            res[1] = Utility.findGoodObject(firstVal);
        }
        return res;
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

