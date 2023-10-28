package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class VariableGetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("VARIABLE_GET_FUNCTION_ARGS_ERROR", "VariableGet don't have enough args.");
        }
        if (args.get(0) instanceof StringResult v) {
            String s = v.getString();
            if (spellContext.getContextMap().hasVariable(s)) {
                Object o = spellContext.getContextMap().getVariable(s);
                if (o instanceof String string) {
                    return new StringResult(string);
                } else if (o instanceof Integer integer) {
                    return new IntegerResult(integer);
                } else if (o instanceof Double aDouble) {
                    return new DoubleResult(aDouble);
                } else if (o instanceof Boolean aBoolean) {
                    return new BooleanResult(aBoolean);
                } else if (o instanceof List<?> list) {
                    return new ListResult(list);
                } else if (o instanceof Set<?> set) {
                    return new SetResult(set);
                } else if (o instanceof Map<?, ?> map) {
                    return new MapResult(map);
                } else if (o instanceof Spell spell) {
                    return new SpellResult(spell);
                } else if (o instanceof Long l) {
                    return new LongResult(l);
                } else if (o instanceof Float f) {
                    return new FloatResult(f);
                } else if (o == null) {
                    return new NullResult();
                } else if (o instanceof ContextMap) {
                    return new ContextMapResult((ContextMap) o);
                } else if (o instanceof ArgumentsResult) {
                    return (ArgumentsResult) o;
                } else if (o instanceof FunctionResult) {
                    return (FunctionResult) o;
                } else {
                    return new ObjectResult(o);
                }

            }
        }
        return args.get(0);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "getVariable";
    }
}
