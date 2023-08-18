package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("SET_FUNCTION_ARGS_ERROR", "Set don't have enough args.");
        }
        Set<Object> resultSet=new HashSet<>();
        for (FunctionResult functionResult : args) {
            if (functionResult instanceof ListResult v) {
                resultSet.add(v.getList());
            }
            if (functionResult instanceof ObjectResult v) {
                resultSet.add(v.getObject());
            }
            if (functionResult instanceof StringResult v) {
                resultSet.add(v.getString());
            }
            if (functionResult instanceof IntegerResult v) {
                resultSet.add(v.getInteger());
            }
            if (functionResult instanceof DoubleResult v) {
                resultSet.add(v.getDouble());
            }
            if (functionResult instanceof BooleanResult v) {
                resultSet.add(v.getBoolean());
            }
            if (functionResult instanceof NullResult) {
                resultSet.add(null);
            }
            if(functionResult instanceof SpellResult spellResult){
                Spell spell=spellResult.getSpell();
                resultSet.add(spell);
            }
            if (functionResult instanceof MapResult v) {
                resultSet.add(v.getMap());
            }
            if (functionResult instanceof SetResult v){
                resultSet.add(v);
            }
        }
        return new SetResult(resultSet);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "set";
    }
}