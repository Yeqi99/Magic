package cn.origincraft.magic.function.results;

public class IntegerResult extends NumberResult {
    public IntegerResult(int integer) {
        super(integer);
    }

    public int getInteger() {
        return (int) getObject();
    }

    @Override
    public String getName() {
        return "Integer";
    }
}
