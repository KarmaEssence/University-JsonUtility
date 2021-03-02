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
}
