package cn.origincraft.magic.function.results;

import cn.origincraft.magic.utils.VariableUtil;

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
        return VariableUtil.stringToNumber(getString());

    }
    public static boolean isNumeric(String str) {
        Number number = VariableUtil.stringToNumber(str);
        return number != null;
    }
}
