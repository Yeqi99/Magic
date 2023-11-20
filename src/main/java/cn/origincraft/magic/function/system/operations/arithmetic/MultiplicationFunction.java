package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class MultiplicationFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id){
            case "A":{
                double result=0;
                for (FunctionResult arg : args) {
                    NumberResult numberResult=null;
                    if (args instanceof NumberResult){
                        numberResult= (NumberResult) arg;
                    }else {
                        numberResult=new NumberResult(arg.toNumber(0));
                    }
                    result*=numberResult.toDouble();
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
                        .addArgType("...")
                        .addInfo("number...")
                        .addInfo("return the result of multiplication calculation")
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
        return "multiplication";
    }
}
