package cn.origincraft.magic.function.results;

import java.util.HashSet;
import java.util.Set;

public class SetResult extends ObjectResult {

    public SetResult(Set<?> value) {
        super(value);
    }

    public Set<?> getSet() {
        return (Set<?>) getObject();
    }

    @Override
    public String getName() {
        return "Set";
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Object arg : getSet()) {
            s.append(arg.toString()).append(",");
        }
        return s.toString();
    }
    public Set<Object> getObjectSet(){
        return new HashSet<>(getSet());
    }
}
