package cn.origincraft.magic.function.system.util.string;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "split";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id){
            case "A":{
                String origin=args.get(0).toString();
                String symbol=args.get(1).toString();
                List<String> strings = new ArrayList<>(Arrays.asList(origin.split(symbol)));
                return new ListResult(strings);
            }
            case "B":{
                String origin=args.get(0).toString();
                String symbol=args.get(1).toString();
                NumberResult numberResult= (NumberResult) args.get(2);
                return new ListResult(Arrays.asList(origin.split(symbol,numberResult.toInteger())));
            }
        }
        return null;
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("String").addArgType("String")
                        .addInfo("string symbol")
                        .addInfo("splitting strings based on specified symbols")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("String").addArgType("String").addArgType("Number")
                        .addInfo("string symbol limit")
                        .addInfo("splitting strings based on specified symbols")
                        .setResultType("List")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
