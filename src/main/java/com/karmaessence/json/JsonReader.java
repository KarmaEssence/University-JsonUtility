package com.karmaessence.json;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

    public Object deserialize(){
        try{
            String line;
            line=flux.readLine();
            if(line != null)
                return makeSubObject((String) cleanValue(flux.readLine())[1]);
        }catch(Exception e){
            e.printStackTrace();

        }
        return null;
    }

    public Map<String,Object> getContent()
    {
        Map<String,Object> jsonArray = new LinkedHashMap<>();

        try{
            String line;
            while ((line=flux.readLine())!=null){
                if(line.contains(":") && line.contains("{")) {
                    Object[] item = cleanValue(line);
                    jsonArray.put((String) item[0], makeSubObject((String) cleanValue(flux.readLine())[1]));
                }else if(line.contains(":") && line.contains("[")){
                    System.out.println("Ligne Object " + line);
                    List<Object> list = makeListObject((String) cleanValue(flux.readLine())[1]);
                    System.out.println(list);
                    jsonArray.put((String) cleanValue(line)[0], list);
                }else{
                    parseLine(jsonArray, line);
                }
            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return jsonArray;
    }

    private Object makeSubObject(String classType) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        classType = classType.replace("\"", "");
        System.out.println("makeList classType : " + classType);
        Class getClass = Class.forName(classType);
        Object res = getClass.getConstructor().newInstance();
        Field[] resFields = res.getClass().getFields();
        int currentIndex = 0;
        String line;
        while (!isEndOfObject((line=flux.readLine()))){
            if(line.contains(":") && line.contains("{")) {
                Object obj = makeSubObject((String) cleanValue(flux.readLine())[1]);
                resFields[currentIndex].setAccessible(true);
                resFields[currentIndex].set(res, obj);
            }else{
                Object[] item = cleanValue(line);
                resFields[currentIndex].setAccessible(true);
                resFields[currentIndex].set(res, item[1]);

            }
            currentIndex += 1;
        }
        return res;
    }

    private List<Object> makeListObject(String classType) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class getClass = Class.forName(classType);
        List<Object> list = (List<Object>) getClass.getConstructor().newInstance();
        String line;
        while (!isEndOfObject((line = flux.readLine()))){
            String[] checkString = cleanString(line);
            System.out.println("Line in while : " + line);
            System.out.println("Clean Line in while : " + cleanString(line)[0]);
            Object[] item = cleanValue(line);
            System.out.println("In While : " + item[0] + " Value in while : " + item[1] );
            if(checkString[1].equals("[")){
                System.out.println("Je suis ici");
                item[1] = makeListObject(cleanString(flux.readLine())[1]);
                list.add(item[1]);
            }else if(!checkString[1].equals("{") && !checkString[0].equals("")) {
                item[1] = makeSubObject(checkString[1]);
                list.add(item[1]);
            }else if(!checkString[1].equals("{")){
                list.add(item[1]);
            }
            System.out.println(list);
        }
        return list;
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

    private String[] cleanString(String line){
        line = line.trim();
        if(!line.contains(":")){
            line = ":" + line;
        }

        String[] item = line.split(":");
        item[0] = item[0].replace("\"","");
        item[1] = item[1].replace(" ","");
        item[1] = item[1].replace(",","");
        item[1]= item[1].replace("\"","");
        return item;
    }

    private Object[] cleanValue(String line){
        String[] cleanString = cleanString(line);
        Object[] res = new Object[2];
        res[0] = cleanString[0];
        if(((String)res[0]).length() > 0 && (((String) res[0]).contains("classType") ||
                Utility.firstCharacterIsUpper(((String) res[0]).charAt(0)))){
            res[1] = cleanString[1];
        }else{
            res[1] = Utility.findGoodObject(cleanString[1]);
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

