package com.karmaessence.json;

import java.io.File;

public class Resources {
    private final static String dataDirectory = "data";

    public static String getPathOfDataDirectory()
    {
        createIfDirectoryNotExist("data");
        return dataDirectory;
    }

    public static boolean checkIfDirectoryExist(String directoryName)
    {
        return createIfDirectoryNotExist(directoryName);
    }

    private static boolean createIfDirectoryNotExist(String directoryName)
    {
        return new File(directoryName).mkdir();
    }

    public static boolean checkIfFileExist(String fileName)
    {
        File check = new File(fileName);
        return check.exists();
    }

    protected static void createIfFileNotExist(String fileName)
    {
        try{
            new File(fileName).createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected static String checkExtensionOf(String fileName,String typeOfFile){
        if(!fileName.endsWith(typeOfFile))
            return fileName + typeOfFile;
        return fileName;
    }


}
