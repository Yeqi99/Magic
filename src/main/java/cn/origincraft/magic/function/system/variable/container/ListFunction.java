package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.Spell;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ListFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.isEmpty()) {
            return new ErrorResult("LIST_FUNCTION_ARGS_ERROR", "List don't have enough args.");
        }
        if (args.size()==1){
            if (args.get(0) instanceof SetResult) {
                SetResult v= (SetResult) args.get(0);
                return new ListResult(new ArrayList<>(v.getSet()));
            }
        }
        List<Object> resultList=new ArrayList<>();
        for (FunctionResult functionResult : args) {
            if (functionResult instanceof ListResult v) {
                resultList.add(v.getList());
            }else if (functionResult instanceof ObjectResult v) {
                resultList.add(v.getObject());
            }else if (functionResult instanceof StringResult v) {
                resultList.add(v.getString());
            }else if (functionResult instanceof IntegerResult v) {
                resultList.add(v.getInteger());
            }else if (functionResult instanceof LongResult v) {
                resultList.add(v.getLong());
            }else if (functionResult instanceof FloatResult v) {
                resultList.add(v.getFloat());
            }else if (functionResult instanceof DoubleResult v) {
                resultList.add(v.getDouble());
            }else if (functionResult instanceof BooleanResult v) {
                resultList.add(v.getBoolean());
            }else if (functionResult instanceof NullResult) {
                resultList.add(null);
            }else if(functionResult instanceof SpellResult spellResult){
                Spell spell=spellResult.getSpell();
                resultList.add(spell);
            }else if (functionResult instanceof MapResult v) {
                resultList.add(v.getMap());
            }else if (functionResult instanceof SetResult v){
                resultList.add(v.getSet());
            }else {
                resultList.add(functionResult);
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
