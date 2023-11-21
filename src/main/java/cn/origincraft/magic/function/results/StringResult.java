package cn.origincraft.magic.function.results;

import cn.origincraft.magic.utils.VariableUtils;

public class StringResult extends ObjectResult {

    public StringResult(String value) {
        super(value);
    }

    public String getString() {
        return (String) getObject();
    }

    @Override
    public String getName() {
        return "String";
    }

    public Number toNumber() {
        if (!isNumeric(getString())) {
            return null;
        }
        return VariableUtils.stringToNumber(getString());

    }
    public static boolean isNumeric(String str) {
        Number number = VariableUtils.stringToNumber(str);
        return number != null;
    }
    @Override
    public int len() {
        return getString().length();
    }
}
