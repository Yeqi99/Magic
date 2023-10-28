package cn.origincraft.magic.function.system.io.output;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class PrintFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        StringBuilder sb=new StringBuilder();
        for (FunctionResult arg : args) {
            if (arg instanceof IntegerResult value){
                sb.append(value.getInteger());
            }else if (arg instanceof DoubleResult value){
                sb.append(value.getDouble());
            }else if (arg instanceof BooleanResult value){
                sb.append(value.getBoolean());
            }else if (arg instanceof StringResult value){
                sb.append(value.getString());
            }else if (arg instanceof ObjectResult value){
                sb.append(value.getObject());
            }
        }
        System.out.println(sb);
        return new NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "print";
    }
}
