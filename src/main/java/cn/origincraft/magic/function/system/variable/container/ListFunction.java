package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.*;

import java.util.ArrayList;
import java.util.List;

public class ListFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("LIST_FUNCTION_ARGS_ERROR", "List don't have enough args.");
        }
        List<Object> resultList=new ArrayList<>();
        for (FunctionResult functionResult : args) {
            if (functionResult instanceof ListResult v) {
                resultList.add(v.getList());
            }
            if (functionResult instanceof ObjectResult v) {
                resultList.add(v.getObject());
            }
            if (functionResult instanceof StringResult v) {
                resultList.add(v.getString());
            }
            if (functionResult instanceof IntegerResult v) {
                resultList.add(v.getInteger());
            }
            if (functionResult instanceof DoubleResult v) {
                resultList.add(v.getDouble());
            }
            if (functionResult instanceof BooleanResult v) {
                resultList.add(v.getBoolean());
            }
            if (functionResult instanceof NullResult) {
                resultList.add(null);
            }
            if(functionResult instanceof SpellResult spellResult){
                Spell spell=spellResult.getSpell();
                resultList.add(spell);
            }
            if (functionResult instanceof MapResult v) {
                resultList.add(v.getMap());
            }
            if (functionResult instanceof SetResult v){
                resultList.add(v.getSet());
            }
        }
        return new ListResult(resultList);
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "list";
    }
}
