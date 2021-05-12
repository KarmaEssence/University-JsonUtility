package com.karmaessence.json;

public class Utility {

    /**
     * To make some space.
     * @param n Number of space.
     * @return All space needed.
     */
    protected static String makeSpace(int n){
        return " ".repeat(Math.max(0, n));
    }

    /**
     * Make some line break.
     * @param n Number of line break.
     * @return All line break needed.
     */
    protected static String makeLineBreak(int n){
        return "\n".repeat(Math.max(0, n));
    }

    /**
     * Verify if obj is primary.
     * @param obj an object.
     * @return true if obj is an instance of primary object.
     */
    protected static boolean isPrimaryVar(Object obj){
        if(obj instanceof Number ||
           obj instanceof String ||
           obj instanceof Boolean ||
           obj instanceof Character){
            return true;
        }
        return false;
    }

    //Modify isDigit for short, long value, etc ...
    protected static Object findGoodObject(String firstVal){
        if(firstVal.length() == 1 && isCharacter(firstVal.charAt(0))){

            return firstVal.charAt(0);
        }else if(isDigit(firstVal)){
            return Integer.valueOf(firstVal);
        }else if(firstVal.equals("true") || firstVal.equals("false")){
            return (firstVal.equals("true"));
        }else{
            return firstVal;
        }
    }

    private static boolean isCharacter(int c){
        return (c >= 58 && c <= 126) || (c >= 33 && c <= 47);
    }

    private static boolean isDigit(String s){
        for(int i = 0; i < s.length(); i++){
            if (!(s.charAt(i) >= 48 && s.charAt(i) <= 57)){
                return false;
            }
        }
        return true;
    }

    protected static boolean firstCharacterIsUpper(char c){
        return (c >= 65 && c <= 90);
    }
}
