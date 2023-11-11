package cn.origincraft.magic.function.results;


public class FloatResult extends NumberResult {

    public FloatResult(float value) {
        super(value);
    }

    public float getFloat() {
        return (float) getObject();
    }

    @Override
    public String getName() {
        return "Float";
    }
}
