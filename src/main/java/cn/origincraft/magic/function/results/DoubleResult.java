package cn.origincraft.magic.function.results;

public class DoubleResult extends NumberResult {

    public DoubleResult(double value) {
        super(value);
    }

    public double getDouble() {
        return (double) getObject();
    }

    @Override
    public String getName() {
        return "Double";
    }
}
