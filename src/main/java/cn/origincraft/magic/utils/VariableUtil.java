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
}
