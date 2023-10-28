package cn.origincraft.magic.utils;

import cn.origincraft.magic.expression.functions.CallableFunction;
import cn.origincraft.magic.expression.functions.FunctionParameter;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodUtil {
    public static List<CallableFunction> sortFunctions(Map<String, Integer> typePriority, List<CallableFunction> functions) {
        List<CallableFunction> sortedFunctions = new ArrayList<>(functions);
        sortedFunctions.sort((o1, o2) -> {
            Integer p1 = typePriority.get(((NormalFunction) o1.getFunction()).getType());
            Integer p2 = typePriority.get(((NormalFunction) o2.getFunction()).getType());

            if (p1 == null) p1 = 0;
            if (p2 == null) p2 = 0;

            return p2.compareTo(p1); // Compare in reverse order
        });
        return sortedFunctions;
    }

    public static boolean isExecuteFunction(FunctionParameter parameter) {
        return parameter instanceof SpellContextParameter;
    }

    public static SpellContext getSpellContext(FunctionParameter parameter) {
        SpellContextParameter spellContextParameter = (SpellContextParameter) parameter;
        return spellContextParameter.getSpellContext();
    }

    public static boolean isFunction(Object object) {
        return object instanceof CallableFunction;
    }

    public static String getSpace(int num) {
        return " ".repeat(num);
    }

    public static String getEnter(int num) {
        return "\n".repeat(num);
    }


}
