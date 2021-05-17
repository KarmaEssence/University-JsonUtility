package com.karmaessence.json;

import com.karmaessence.json.annotation.JsonSerializable;
import com.karmaessence.json.annotation.JsonSerializableType;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
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

    public void serializeObject(Object object){
        Field[] fields = object.getClass().getFields();
        Resources.checkIfFileExist(filePath);
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write("{" + Utility.makeLineBreak(1));
            savePrimaryVar(object.getClass().getName(), writer, "classType", false, 4);
            for(Field field: fields){
                if(field.getAnnotation(JsonSerializable.class) == null ||
                        field.getAnnotation(JsonSerializable.class).getType().equals(JsonSerializableType.DeserialisableOnly))
                    continue;

                if(Utility.isPrimaryVar(field.get(object))) {
                    savePrimaryVar(field.get(object), writer, field.getName(), field == fields[fields.length-1], 4);
                }else if(field.get(object) instanceof List) {
                    saveList((List<Object>) field.get(object), writer, field.getName(), field == fields[fields.length-1], 4);
                }else if(field.get(object) instanceof Map){
                    saveMap((Map<String, Object>) field.get(object), writer, field.getName(), field == fields[fields.length-1], 4);
                }else{
                    saveSpecialObject(field.get(object), writer, field.getName(), field == fields[fields.length-1], 4);
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
     * Save the map in file (in data folder).
     */
    public void saveMap()
    {
        Resources.checkIfFileExist(filePath);
        try{
            FileWriter writer = new FileWriter(filePath);
            writer.write("{" + Utility.makeLineBreak(1));
            Set<String> keys = jsonMap.keySet();
            for (String key: keys){
                if(Utility.isPrimaryVar(jsonMap.get(key))) {
                    savePrimaryVar(writer,key, 4);
                }else if(jsonMap.get(key) instanceof List) {
                    saveList((List<Object>) jsonMap.get(key), writer, key, key == keys.toArray()[keys.size() - 1], 4);
                }else if(jsonMap.get(key) instanceof Map){
                    saveMap((Map<String, Object>) jsonMap.get(key), writer, key, key == keys.toArray()[keys.size() - 1], 4);
                }else{
                    saveSpecialObject(jsonMap.get(key), writer, key, key == keys.toArray()[keys.size() - 1], 4);
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
        String displayKey = (!isUselessKey(key))? "\""+ key + "\": " : "";
        writer.write(Utility.makeSpace(space) + displayKey + obj + comma + "\n");
    }

    private void saveList(List<Object> obj, FileWriter writer, String key, boolean isLastKey,int space) throws IOException, IllegalAccessException {

        writer.write(Utility.makeSpace(space)  + "\""+ key + "\": " + "[" + Utility.makeLineBreak(1));
        savePrimaryVar(obj.getClass().getName(), writer, "classType", false, space + 4);
        Object[] objects = obj.toArray();
        for(Object object: objects){
            if(Utility.isPrimaryVar(object)) {
                savePrimaryVar(object, writer, null,object == objects[objects.length-1], space + 8);
            }else if(object instanceof List) {
                saveList((List<Object>) object, writer, key, object == objects[objects.length-1], space + 4);
            }else if(object instanceof Map){
                saveMap((Map<String, Object>) object, writer, key, object == objects[objects.length-1], space + 4);
            }else{
                saveSpecialObject(object, writer, null, object == objects[objects.length-1], space + 8);
            }
        }
        String comma = (isLastKey)?"":",";
        writer.write(Utility.makeSpace(space)  +  "]" + comma + Utility.makeLineBreak(1));
    }

    //Ne marche pas du tout, changer en map Object,Object
    private void saveMap(Map<String, Object> obj, FileWriter writer, String key, boolean isLastKey,int space) throws IOException, IllegalAccessException {
        writer.write(Utility.makeSpace(space)  + "\""+ key + "\": " + "[" + Utility.makeLineBreak(1));
        savePrimaryVar(obj.getClass().getName(), writer, "classType", false, space + 4);
        String[] objects = (String[]) obj.keySet().toArray();
        for(String object: objects){
            if(Utility.isPrimaryVar(object)) {
                savePrimaryVar(obj.get(object), writer, object, object.equals(objects[objects.length - 1]), space + 8);
            }else if(obj.get(object) instanceof List) {
                saveList((List<Object>) obj.get(object), writer, key, object.equals(objects[objects.length - 1]), space + 4);
            }else if(obj.get(object) instanceof Map){
                saveMap((Map<String, Object>) obj.get(object), writer, key, object.equals(objects[objects.length - 1]), space + 4);
            }else{
                saveSpecialObject(object, writer, object, object.equals(objects[objects.length - 1]), space + 8);
            }
        }
        String comma = (isLastKey)?"":",";
        writer.write(Utility.makeSpace(space)  +  "]" + comma + Utility.makeLineBreak(1));
    }

    //Save content with the annotation "JsonSerialisable"

    private void saveSpecialObject(Object obj, FileWriter writer, String key, boolean isLastKey,int space) throws IOException, IllegalAccessException {
        Class getClass = obj.getClass();
        Field[] classField = getClass.getDeclaredFields();
        String displayKey = (!isUselessKey(key))? "\""+ key + "\": " : "";
        writer.write(Utility.makeSpace(space)  + displayKey + "{" + Utility.makeLineBreak(1));
        savePrimaryVar(obj.getClass().getName(), writer, "classType", false, space + 4);
        for(Field field: classField){
            if(Utility.isPrimaryVar(field.get(obj))){
                savePrimaryVar(field.get(obj),writer, field.getName(), classField[classField.length-1] == field, space + 4);
            }else{
                saveSpecialObject(field.get(obj), writer, field.getName(), classField[classField.length-1] == field,space + 4);
            }
        }
        String comma = (isLastKey)?"":",";
        writer.write(Utility.makeSpace(space) + "}" + comma + Utility.makeLineBreak(1));
    }

    private boolean isUselessKey(String key){
        return key == null || key.equals("{");
    }
}
