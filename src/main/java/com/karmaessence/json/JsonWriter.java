package com.karmaessence.json;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JsonWriter {

    private String filePath;
    private Map<String,Object> jsonMap;
    private String lastKey;

    /**
     * Create an instance of JsonWriter.
     * @param filePath File path.
     */
    public JsonWriter(String filePath)
    {
        filePath = Resources.checkExtensionOf(filePath,".json");
        if(Resources.checkIfFileExist(filePath)){
            Resources.createIfFileNotExist(filePath);
        }
        this.filePath = filePath;
        jsonMap = new LinkedHashMap<>();
    }

    /**
     * @return File path.
     */
    public String getFilePath()
    {
        return filePath;
    }

    /**
     * Fill the map with key and value.
     * @param key Key.
     * @param value Value.
     */
    public void fillJsonMap(String key,Object value)
    {
        jsonMap.put(key,value);
        lastKey = key;
    }

    //https://forums.commentcamarche.net/forum/affich-25720907-ecrire-en-java-dans-un-fichier-json
    //

    /**
     * Save the map in file (in data folder).
     */
    public void save()
    {
        Resources.checkIfFileExist(filePath);
        try{
            FileWriter writer = new FileWriter(filePath);
            writer.write("{" + Utility.makeLineBreak(1));
            Set<String> keys = jsonMap.keySet();
            for (String key: keys){
                if(!Utility.isPrimaryVar(jsonMap.get(key))){
                    //System.out.println(key == keys.toArray()[keys.size()-1]);
                    saveSpecialObject(jsonMap.get(key), writer, key, key == keys.toArray()[keys.size()-1], 4);
                }else{
                    savePrimaryVar(writer,key, 4);
                }
            }
            writer.write("}");
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    /**
     * Check if the object is primary.
     * @param writer To write the content.
     * @param key Current key.
     * @throws IOException In case where the save failed.
     */
    private void savePrimaryVar(FileWriter writer, String key, int space) throws IOException {
        Object obj = jsonMap.get(key);
        if (obj instanceof String || obj instanceof Character){
            obj = "\"" + obj + "\"";
        }
        String comma = (key.equals(lastKey))?"":",";
        writer.write(Utility.makeSpace(space) + "\""+ key + "\": " + obj + comma + "\n");
    }

    private void savePrimaryVar(Object obj, FileWriter writer,String key, boolean isLastKey, int space) throws IOException {
        if (obj instanceof String || obj instanceof Character){
            obj = "\"" + obj + "\"";
        }
        String comma = (isLastKey)?"":",";
        writer.write(Utility.makeSpace(space) + "\""+ key + "\": " + obj + comma + "\n");
    }

    //Save content with the annotation "JsonSerialisable"

    private void saveSpecialObject(Object obj, FileWriter writer, String key, boolean isLastKey,int space) throws IOException, IllegalAccessException {
        Class getClass = obj.getClass();
        Field[] classField = getClass.getDeclaredFields();
        writer.write(Utility.makeSpace(space)  + "\""+ key + "\": " + "{" + Utility.makeLineBreak(1));
        savePrimaryVar(obj.getClass().getName(), writer, "classType", false, space + 4);
        for(Field field: classField){
            if(Utility.isPrimaryVar(field.get(obj))){
                savePrimaryVar(field.get(obj),writer, field.getName(), classField[classField.length-1] == field, space + 4);
            }else{
                saveSpecialObject(field.get(obj), writer, field.getName(), classField[classField.length-1] == field,space + 4);
            }
        }
        String comma = (isLastKey)?"":",";
        writer.write(Utility.makeSpace(space)  +  "}" + comma + Utility.makeLineBreak(1));
    }
}
