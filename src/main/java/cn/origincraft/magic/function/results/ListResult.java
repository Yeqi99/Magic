package cn.origincraft.magic.function.results;

import java.util.List;

public class ListResult extends ObjectResult {

    public ListResult(List<?> value) {
        super(value);
    }

    public List<?> getList() {
        return (List<?>) getObject();
    }

    @Override
    public String getName() {
        return "List";
    }
}
