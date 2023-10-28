package cn.origincraft.magic.function.results;


public class NullResult extends ObjectResult{
    public NullResult() {
        super(null);
    }
    @Override
    public String getName() {
        return "Null";
    }
}
