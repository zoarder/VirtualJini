package com.technologies.virtualjini.utils;

public class StringUtils {

    // Remove first and last character from a string
    public static String removeFirstAndLast(String data) {

        return data.substring(1, data.length() - 1);
    }

    public static boolean isNullOrEmpty(String myString) {
        if (myString == null) {
            return true;
        }
        if (myString.length() == 0 || myString.equalsIgnoreCase("null")
                || myString.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

}
