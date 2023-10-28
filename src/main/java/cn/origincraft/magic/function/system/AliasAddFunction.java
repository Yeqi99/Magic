package cn.origincraft.magic.function.system;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class AliasAddFunction extends NormalFunction {

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size()<2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "AliasAdd function requires at least two arguments.");
        }
        FunctionResult real = args.get(0);
        List<FunctionResult> aliases = args.subList(1, args.size());
        if (real instanceof StringResult){
            StringResult realStringResult = (StringResult) real;
            String realString = realStringResult.getString();
            for (FunctionResult alias:aliases){
                if (alias instanceof StringResult){
                    StringResult aliasStringResult = (StringResult) alias;
                    String aliasString = aliasStringResult.getString();
                    spellContext.getMagicManager().addAlias(realString,aliasString);
                }
            }
        }else {
            return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
        }
        return new  NullResult();
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "aliasAdd";
    }
}
