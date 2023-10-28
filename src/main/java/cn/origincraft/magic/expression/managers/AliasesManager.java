package cn.origincraft.magic.expression.managers;

import java.util.*;

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
        this.aliases.put(realName, new ArrayList<>(Arrays.asList(aliases)));
    }

    public boolean isRealName(String name) {
        if (aliases.containsKey(name)) {
            return true;
        } else {
            List<String> allAliases = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : aliases.entrySet()) {
                allAliases.addAll(entry.getValue());
            }
            if (allAliases.contains(name)) return true;
        }
        throw new IllegalArgumentException("Check real name with unknown value");
    }

    public void addAlias(String realName, String alias, FunctionManager functionManager) {
        if (!functionManager.getRegistry().containsKey(realName)) throw new IllegalArgumentException("Function " + realName + " not found or not registered");
        if (!aliases.containsKey(realName)) {
            aliases.put(realName, new ArrayList<>());
            this.aliases.get(realName).add(alias);
            return;
        }
        if (aliases.get(realName).contains(alias)) {
            throw new RuntimeException(new IllegalArgumentException("Duplicate registration alias: " + alias));
        }
        this.aliases.get(realName).add(alias);
    }
}
