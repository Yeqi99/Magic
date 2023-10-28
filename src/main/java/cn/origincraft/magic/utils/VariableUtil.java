package cn.origincraft.magic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableUtil {
    public static boolean tryInt(String s) {
        Pattern pattern = Pattern.compile("^-?\\d+$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean tryBoolean(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }

    public static boolean tryDouble(String s) {
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean isInt(Object obj) {
        return obj instanceof Integer;
    }

    public static boolean isDouble(Object obj) {
        return obj instanceof Double;
    }

    public static boolean isFloat(Object obj) {
        return obj instanceof Float;
    }

    public static boolean isString(Object obj) {
        return obj instanceof String;
    }

    public static boolean isBoolean(Object obj) {
        return obj instanceof Boolean;
    }

    public static boolean hasFractionalPart(double d) {
        return d != (int) d;
    }
}
