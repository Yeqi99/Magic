package cn.origincraft.magic.function.results;

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
}
