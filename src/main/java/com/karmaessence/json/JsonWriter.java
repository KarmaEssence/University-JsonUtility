package com.karmaessence.json;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonWriter {

    private String filePath;
    private Map<String,Object> jsonMap;

    public JsonWriter(String filePath)
    {
        filePath = Resources.checkExtensionOf(filePath,".json");
        if(Resources.checkIfFileExist(filePath)){
            Resources.createIfFileNotExist(filePath);
        }
        this.filePath = filePath;
        jsonMap = new HashMap<>();
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void fillJsonMap(String key,Object value){
        jsonMap.put(key,value);
    }

    //https://forums.commentcamarche.net/forum/affich-25720907-ecrire-en-java-dans-un-fichier-json
    //

    public void save()
    {
        try{
            FileWriter writer = new FileWriter(filePath);
            writer.write("{" + Utility.makeLineBreak(1));
            for (String key: jsonMap.keySet()){
                writer.write(Utility.makeSpace(4) + "\""+ key + "\" : " + jsonMap.get(key));
            }
            writer.write(Utility.makeLineBreak(1) + "}");
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

}
