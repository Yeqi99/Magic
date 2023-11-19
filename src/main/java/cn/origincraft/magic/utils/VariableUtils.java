package cn.origincraft.magic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableUtils {
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
    public static Number stringToNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e1) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException e2) {
                try {
                    return Float.parseFloat(str);
                } catch (NumberFormatException e3) {
                    try {
                        return Double.parseDouble(str);
                    }catch (NumberFormatException e4) {
                        return null;
                    }
                }
            }
        }
    }
    public static Number stringToNumber(String str,Number defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e1) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException e2) {
                try {
                    return Float.parseFloat(str);
                } catch (NumberFormatException e3) {
                    try {
                        return Double.parseDouble(str);
                    }catch (NumberFormatException e4) {
                        return defaultValue;
                    }
                }
            }
        }
    }
    public static Number stringToNumber(String str,String type,Number defaultValue) {
        switch (type){
            case "int":{
                try {
                    return Integer.parseInt(str);
                } catch (NumberFormatException e1) {
                    return defaultValue;
                }
            }
            case "double":{
                try {
                    return Double.parseDouble(str);
                }catch (NumberFormatException e4) {
                    return defaultValue;
                }
            }
            case "float":{
                try {
                    return Float.parseFloat(str);
                } catch (NumberFormatException e3) {
                    return defaultValue;
                }
            }
            case "long":{
                try {
                    return Long.parseLong(str);
                } catch (NumberFormatException e2) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }
    public static String getNumberType(Number number) {
        if (number instanceof Integer) {
            return "int";
        } else if (number instanceof Double) {
            return "double";
        } else if (number instanceof Float) {
            return "float";
        } else if (number instanceof Long) {
            return "long";
        } else {
            return "unknown";
        }
    }
    public static boolean numberToBoolean(Number number,boolean defaultValue) {
        if (number instanceof Integer) {
            return number.intValue() != 0;
        } else if (number instanceof Double) {
            return number.doubleValue() != 0;
        } else if (number instanceof Float) {
            return number.floatValue() != 0;
        } else if (number instanceof Long) {
            return number.longValue() != 0;
        } else {
            return defaultValue;
        }
    }
    public static boolean numberToBoolean(Number number) {
        if (number instanceof Integer) {
            return number.intValue() != 0;
        } else if (number instanceof Double) {
            return number.doubleValue() != 0;
        } else if (number instanceof Float) {
            return number.floatValue() != 0;
        } else if (number instanceof Long) {
            return number.longValue() != 0;
        } else {
            return false;
        }
    }
    public static boolean hasFractionalPart(double d) {
        return d != (int) d;
    }
}
