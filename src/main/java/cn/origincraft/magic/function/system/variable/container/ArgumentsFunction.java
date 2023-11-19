package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ArgumentsFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id){
            case "A":{
                Object object=args.get(0).getObject();
                if (!(object instanceof List<?>)){
                    return new ErrorResult("TYPE_ERROR","Object convert to arguments failed");
                }
                return new ArgumentsResult((List<FunctionResult>) object);
            }
            case "B":{
                List<?> arguments= (List<?>) args.get(0).getObject();
                NumberResult numberResult = (NumberResult) args.get(1);
                return (FunctionResult) arguments.get(numberResult.toInteger());
            }
            case "C":{
                return new ArgumentsResult(args);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType(".")
                        .addInfo("object")
                        .addInfo("Convert object to list")
                        .setResultType("Arguments")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("Arguments").addArgType("Number")
                        .addInfo("arguments index")
                        .addInfo("Get element from arguments by index")
                        .setResultType("Arguments")
        );
        argsSettings.add(
                new ArgsSetting("C")
                        .addArgType("...")
                        .addInfo("args")
                        .addInfo("Create a arguments.")
                        .addInfo("use unpack to unpack arguments.")
                        .addInfo("unpack-format:arg1,arg2,arg3...")
                        .setResultType("Arguments")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "arguments";
    }
}
