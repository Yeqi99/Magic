package cn.origincraft.magic.function.results;

import cn.origincraft.magic.utils.VariableUtil;

public class NumberResult extends ObjectResult{
    public NumberResult(Number number) {
        super(number);
    }
    public NumberResult(String number) {
        super(VariableUtil.stringToNumber(number)==null?VariableUtil.stringToNumber(number):0);
    }
    public NumberResult(String number,Number defaultValue) {
        super(VariableUtil.stringToNumber(number)==null?VariableUtil.stringToNumber(number):defaultValue);
    }
    public Number getNumber() {
        return (Number) getObject();
    }
    public String getName() {
        return "Number";
    }
    public boolean isInteger() {
        return getObject() instanceof Integer;
    }
    public boolean isLong() {
        return getObject() instanceof Long;
    }
    public boolean isDouble() {
        return getObject() instanceof Double;
    }
    public boolean isFloat() {
        return getObject() instanceof Float;
    }
    public boolean isShort() {
        return getObject() instanceof Short;
    }
    public boolean isByte() {
        return getObject() instanceof Byte;
    }
    public String getNumberType() {
        return VariableUtil.getNumberType(getNumber());
    }
    public boolean toBoolean() {
        return VariableUtil.numberToBoolean(getNumber());
    }
}
