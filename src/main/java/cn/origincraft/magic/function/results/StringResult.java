package cn.origincraft.magic.function.results;

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
}
