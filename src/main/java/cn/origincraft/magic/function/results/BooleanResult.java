package cn.origincraft.magic.function.results;

public class BooleanResult extends ObjectResult{

    public BooleanResult(boolean value) {
        super(value);
    }

    public boolean getBoolean() {
        return (boolean) getObject();
    }

    @Override
    public String getName() {
        return "Boolean";
    }
}
