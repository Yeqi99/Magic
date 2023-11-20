package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtils;

import java.util.ArrayList;
import java.util.List;

public class IntegerDivisionFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        switch (id){
            case "A":{
                NumberResult dividend= (NumberResult) args.get(0);
                long result = dividend.toLong();
                for (FunctionResult functionResult : args.subList(1, args.size())) {
                    NumberResult numberResult=null;
                    if (args instanceof NumberResult){
                        numberResult= (NumberResult) functionResult;
                    }else {
                        numberResult=new NumberResult(functionResult.toNumber(0));
                    }
                    if (numberResult.toLong()==0){
                        return new ErrorResult("ARGS_ERROR","Divider cannot be 0");
                    }
                    result/=numberResult.toLong();
                }
                return new NumberResult(result);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("Number").addArgType("...")
                        .addInfo("dividend ...")
                        .addInfo("return the result of integer division calculation")
                        .setResultType("Number")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "integerDivision";
    }
}