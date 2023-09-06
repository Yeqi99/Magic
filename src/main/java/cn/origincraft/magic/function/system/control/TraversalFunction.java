package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.SetResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.results.ObjectResult;
import dev.rgbmc.expression.results.StringResult;

import java.util.List;
import java.util.Set;

public class TraversalFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<3){
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "Traversal function requires at least three arguments.");
        }
        FunctionResult container=args.get(0);
        FunctionResult value=args.get(1);
        FunctionResult index=args.get(2);
        if (!(value instanceof StringResult && index instanceof StringResult)){
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }

        String valueString=((StringResult) value).getString();
        String indexString=((StringResult) index).getString();
        if (!spellContext.getContextMap().hasVariable(indexString)){
            spellContext.getContextMap().putVariable(indexString, 0);
        }
        Object oi= spellContext.getContextMap().getVariable(indexString);
        if (!(oi instanceof Integer)){
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        int i= (int) oi;
        if (container instanceof ListResult){
            List<Object> list=((ListResult) container).getList();
            if (i<list.size()){
                Object o=list.get(i);
                ObjectResult objectResult=new ObjectResult(o);
                spellContext.getContextMap().putVariable(valueString, objectResult);
                spellContext.getContextMap().putVariable(indexString, i+1);
                spellContext.putExecuteNext(spellContext.getExecuteIndex());
                return new StringResult(valueString);
            }else {
                spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(),false);
                return new StringResult(valueString);
            }
        }else if (container instanceof ArgumentsResult){
            List<FunctionResult> list=((ArgumentsResult) container).getArgs();
            if (i<list.size()){
                FunctionResult o=list.get(i);
                spellContext.getContextMap().putVariable(valueString, o);
                spellContext.getContextMap().putVariable(indexString, i+1);
                spellContext.putExecuteNext(spellContext.getExecuteIndex());
                return new StringResult(valueString);
            }else {
                spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(),false);
                return new StringResult(valueString);
            }
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "traversal";
    }
}
