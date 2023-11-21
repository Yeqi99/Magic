package cn.origincraft.magic.function.system.operations.arithmetic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.ParseType;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class SelfComputingFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "selfComputing";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id){
            case "A":{
                String varName=args.get(0).toString();
                if (!spellContext.getContextMap().hasVariable(varName)){
                    return new ErrorResult("VARIABLE_NO_FOUND",getName()+" unable to find variable named "+varName);
                }
                Object object=spellContext.getContextMap().getVariable(varName);
                Number number=((ObjectResult) object).toNumber(0);
                NumberResult numberResult=new NumberResult(number);
                double result = numberResult.toDouble();
                for (FunctionResult functionResult : args.subList(1, args.size())) {
                    String action=functionResult.toString();
                    if (action.startsWith("+")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result+= tempNum.toDouble();
                    }else if (action.startsWith("-")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result-= tempNum.toDouble();
                    }else if (action.startsWith("*")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result*= tempNum.toDouble();
                    }else if (action.startsWith("/")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result/= tempNum.toDouble();
                    }else if (action.startsWith("%")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result%= tempNum.toDouble();
                    }else if (action.startsWith("^")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result= Math.pow(result,tempNum.toDouble());
                    }else if (action.startsWith("//")){
                        NumberResult tempNum=new NumberResult(action.substring(2));
                        result= (double) (((long) result) / tempNum.toLong());
                    }
                }
                return new NumberResult(result);
            }
            case "B":{
                NumberResult numberResult= (NumberResult) args.get(0);
                double result = numberResult.toDouble();
                for (FunctionResult functionResult : args.subList(1, args.size())) {
                    String action=functionResult.toString();
                    if (action.startsWith("+")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result+= tempNum.toDouble();
                    }else if (action.startsWith("-")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result-= tempNum.toDouble();
                    }else if (action.startsWith("*")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result+= tempNum.toDouble();
                    }else if (action.startsWith("/")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result/= tempNum.toDouble();
                    }else if (action.startsWith("%")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result%= tempNum.toDouble();
                    }else if (action.startsWith("^")){
                        NumberResult tempNum=new NumberResult(action.substring(1));
                        result+= Math.pow(result,tempNum.toDouble());
                    }else if (action.startsWith("//")){
                        NumberResult tempNum=new NumberResult(action.substring(2));
                        result+= (double) (((long) result) / tempNum.toLong());
                    }
                }
                return new NumberResult(result);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        setParseType(0, ParseType.ORIGIN);
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("String").addArgType("...")
                        .addInfo("varName(Number) action ...")
                        .addInfo("change a variable by name and action")
                        .addInfo("action:+,-,*,/,^,%,//")
                        .setResultType("Number")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("Number").addArgType("...")
                        .addInfo("Number action ...")
                        .addInfo("change a variable by name and action")
                        .addInfo("action:+,-,*,/,^,%,//")
                        .setResultType("Number")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
