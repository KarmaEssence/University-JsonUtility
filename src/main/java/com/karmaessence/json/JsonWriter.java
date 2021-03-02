package com.karmaessence.json;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

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
        jsonMap = new HashMap<>();
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
        try{
            FileWriter writer = new FileWriter(filePath);
            writer.write("{" + Utility.makeLineBreak(1));
            for (String key: jsonMap.keySet()){
                if(!Utility.isPrimaryVar(jsonMap.get(key))){
                    //saveSpecialItem(jsonMap.get(key), writer, key);
                }else{
                    savePrimaryVar(writer,key);
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
    private void savePrimaryVar(FileWriter writer,String key) throws IOException {
        Object obj = jsonMap.get(key);
        if (obj instanceof String || obj instanceof Character){
            obj = "\"" + obj + "\"";
        }
        String comma = (key.equals(lastKey))?"":",";
        writer.write(Utility.makeSpace(4) + "\""+ key + "\" : " + obj + comma + "\n");
    }

    //Save content with the annotation "JsonSerialisable"
    /*private void saveSpecialItem(Object obj,FileWriter writer, String key) throws IllegalAccessException, IOException {
        Field[] classField = obj.getClass().getDeclaredFields();
        for(Field field: classField){
            if (Modifier.isStatic(field.getModifiers()))
            {
                field.get(null);
            }
            String renamedField = field.getName();
            writer.write(Utility.makeSpace(4) + "\""+ renamedField + "\" : " + obj. + "\n");
        }
    }*/

}
