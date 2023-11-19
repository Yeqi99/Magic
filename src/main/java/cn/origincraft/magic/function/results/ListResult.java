package cn.origincraft.magic.function.results;

import java.util.ArrayList;
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Object arg : getList()) {
            s.append(arg.toString()).append(",");
        }
        return s.toString();
    }
    public List<Object> getObjectList(){
        return new ArrayList<>(getList());
    }
}
