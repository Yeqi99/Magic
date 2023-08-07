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
    // 检查指定字符串是否是别名或真名
    public boolean isAliasOrRealName(String name) {
        for (Map.Entry<String, List<String>> entry : aliases.entrySet()) {
            if (entry.getValue().contains(name)) {
                return true;
            }
        }
        return aliases.containsKey(name);
    }
    // 给某个真名添加别名 但是要检查是否已经存在 存在再添加会返回false
    public boolean addAlias(String realName, String alias) {
        if (aliases.containsKey(realName)) {
            if (aliases.get(realName).contains(alias)) {
                return false;
            }
            aliases.get(realName).add(alias);
            return true;
        }
        return false;
    }

}