package cn.origincraft.magic.interpreter.fastexpression.managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AliasesManager {
    private final Map<String, List<String>> aliases;

    public AliasesManager() {
        aliases = new HashMap<>();
    }

    public String getRealName(String alias) {
        for (Map.Entry<String, List<String>> entry : aliases.entrySet()) {
            if (entry.getValue().contains(alias)) {
                return entry.getKey();
            }
        }
        return alias;
    }

    public void registerAliases(String realName, String... aliases) {
        this.aliases.put(realName, Arrays.asList(aliases));
    }
}