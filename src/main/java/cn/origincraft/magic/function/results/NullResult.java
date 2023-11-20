package cn.origincraft.magic.function.results;


public class NullResult extends ObjectResult {
    public NullResult() {
        super();
    }

    @Override
    public String getName() {
        return "Null";
    }

    @Override
    public String toString() {
        return "null";
    }
}
