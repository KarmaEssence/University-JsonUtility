package com.karmaessence.json;

public class Utility {

    protected static String makeSpace(int n){
        return " ".repeat(Math.max(0, n));
    }

    protected static String makeLineBreak(int n){
        return "\n".repeat(Math.max(0, n));
    }
}
