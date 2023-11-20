package cn.origincraft.magic.function.results;

import cn.origincraft.magic.utils.VariableUtils;

public class NumberResult extends ObjectResult {
    public NumberResult(Number number) {
        super(number);
    }

    public NumberResult(String number) {
        super(VariableUtils.stringToNumber(number) != null ? VariableUtils.stringToNumber(number) : 0);
    }

    public NumberResult(String number, Number defaultValue) {
        super(VariableUtils.stringToNumber(number) != null ? VariableUtils.stringToNumber(number) : defaultValue);
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
        return VariableUtils.getNumberType(getNumber());
    }

    public boolean toBoolean() {
        return VariableUtils.numberToBoolean(getNumber());
    }

    public int toInteger() {
        if (isInteger()) {
            return getNumber().intValue();
        }
        if (isLong()) {
            return (int) getNumber().longValue();
        }
        if (isFloat()) {
            return (int) getNumber().floatValue();
        }
        if (isDouble()) {
            return (int) getNumber().doubleValue();
        }
        if (isShort()){
            return getNumber().shortValue();
        }
        return 0;
    }
    public long toLong() {
        if (isInteger()) {
            return getNumber().longValue();
        }
        if (isLong()) {
            return getNumber().longValue();
        }
        if (isFloat()) {
            return (long) getNumber().floatValue();
        }
        if (isDouble()) {
            return (long) getNumber().doubleValue();
        }
        if (isShort()) {
            return getNumber().shortValue();
        }
        return 0L;
    }

    public float toFloat() {
        if (isInteger()) {
            return getNumber().floatValue();
        }
        if (isLong()) {
            return getNumber().floatValue();
        }
        if (isFloat()) {
            return getNumber().floatValue();
        }
        if (isDouble()) {
            return (float) getNumber().doubleValue();
        }
        if (isShort()) {
            return getNumber().shortValue();
        }
        return 0.0f;
    }

    public double toDouble() {
        if (isInteger()) {
            return getNumber().doubleValue();
        }
        if (isLong()) {
            return getNumber().doubleValue();
        }
        if (isFloat()) {
            return getNumber().doubleValue();
        }
        if (isDouble()) {
            return getNumber().doubleValue();
        }
        if (isShort()) {
            return getNumber().shortValue();
        }
        return 0.0;
    }

    public short toShort() {
        if (isInteger()) {
            return getNumber().shortValue();
        }
        if (isLong()) {
            return getNumber().shortValue();
        }
        if (isFloat()) {
            return getNumber().shortValue();
        }
        if (isDouble()) {
            return getNumber().shortValue();
        }
        if (isShort()) {
            return getNumber().shortValue();
        }
        return 0;
    }

    @Override
    public String toString() {
        if (isInteger()){
            return getNumber().intValue()+"";
        }
        if (isLong()){
            return getNumber().intValue()+"";
        }
        if (isFloat()){
            return getNumber().floatValue()+"";
        }
        if (isDouble()){
            return getNumber().doubleValue()+"";
        }
        if (isShort()){
            return getNumber().shortValue()+"";
        }
        if (isByte()) {
            return getNumber().byteValue() + "";
        }
        return 0+"";
    }
}
