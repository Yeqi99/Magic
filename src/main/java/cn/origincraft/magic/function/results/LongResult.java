package cn.origincraft.magic.function.results;


public class LongResult extends NumberResult {

    public LongResult(long value) {
        super(value);
    }


    public long getLong() {
        return (long) getObject();
    }


    @Override
    public String getName() {
        return "Long";
    }

}
