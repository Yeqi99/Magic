package cn.origincraft.magic.function.system.variable.define;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.HasVariableFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class VariableDefineFunction extends HasVariableFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("VARIABLE_DEFINE_FUNCTION_ARGS_ERROR", "Variable define don't have enough args.");
        }
        String varName;
        if (args.get(0) instanceof StringResult name) {
            varName = name.getString();
        } else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        if (args.get(1) instanceof StringResult v) {
            spellContext.getContextMap().putVariable(varName, v.getString());
        } else if (args.get(1) instanceof DoubleResult v) {
            spellContext.getContextMap().putVariable(varName, v.getDouble());
        } else if (args.get(1) instanceof BooleanResult v) {
            spellContext.getContextMap().putVariable(varName, v.getBoolean());
        } else if (args.get(1) instanceof IntegerResult v) {
            spellContext.getContextMap().putVariable(varName, v.getInteger());
        } else if (args.get(1) instanceof LongResult v) {
            spellContext.getContextMap().putVariable(varName, v.getLong());
        } else if (args.get(1) instanceof FloatResult v) {
            spellContext.getContextMap().putVariable(varName, v.getFloat());
        } else if (args.get(1) instanceof ListResult v) {
            spellContext.getContextMap().putVariable(varName, v.getList());
        } else if (args.get(1) instanceof MapResult v) {
            spellContext.getContextMap().putVariable(varName, v.getMap());
        } else if (args.get(1) instanceof SetResult v) {
            spellContext.getContextMap().putVariable(varName, v.getSet());
        } else if (args.get(1) instanceof SpellResult v) {
            spellContext.getContextMap().putVariable(varName, v.getSpell());
        } else if (args.get(1) instanceof SpellResult v) {
            spellContext.getContextMap().putVariable(varName, v.getSpell());
        } else if (args.get(1) instanceof NullResult) {
            spellContext.getContextMap().putVariable(varName, null);
        } else if (args.get(1) instanceof ContextMapResult v) {
            spellContext.getContextMap().putVariable(varName, v.getContextMap());
        } else if (args.get(1) instanceof ArgumentsResult v) {
            spellContext.getContextMap().putVariable(varName, v);
        } else {
            spellContext.getContextMap().putVariable(varName, args.get(1));
        }
        return new NullResult();

    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "variableDefine";
    }
}
