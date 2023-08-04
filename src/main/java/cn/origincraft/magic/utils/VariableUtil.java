package cn.origincraft.magic.utils;

public class VariableUtil {
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
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
    public static boolean hasFractionalPart(double d) {
        return d != (int) d;
    }
}
