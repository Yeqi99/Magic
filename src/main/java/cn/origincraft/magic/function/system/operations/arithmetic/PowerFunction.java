package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class PowerFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        switch (id){
            case "A":{
                NumberResult base= (NumberResult) args.get(0);
                double result = base.toDouble();
                for (FunctionResult functionResult : args.subList(1, args.size())) {
                    NumberResult numberResult=null;
                    if (args instanceof NumberResult){
                        numberResult= (NumberResult) functionResult;
                    }else {
                        numberResult=new NumberResult(functionResult.toNumber(0));
                    }
                    result= Math.pow(result,numberResult.toDouble());
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
                        .addInfo("base number ...")
                        .addInfo("return the result of a power calculation")
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
        return "power";
    }
}