package cn.origincraft.magic.function.system.variable.container;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ArgumentsFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        return new ArgumentsResult(args);
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
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
