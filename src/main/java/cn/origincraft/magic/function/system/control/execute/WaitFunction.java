package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.ArrayList;
import java.util.List;

public class WaitFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "wait";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                String time = (String) args.get(0).getObject();
                if (!VariableUtil.tryInt(time)) {
                    return new ErrorResult("TIME_ERROR", "The time must be a number str");
                }
                try {
                    Thread.sleep(Long.parseLong(time));
                } catch (InterruptedException e) {
                    return new ErrorResult("TIME_ERROR", "The time must be a number str");
                }
                break;
            }
            case "B": {
                String time = (String) args.get(0).getObject();
                String unit = (String) args.get(1).getObject();
                if (!VariableUtil.tryInt(time)) {
                    return new ErrorResult("TIME_ERROR", "The time must be a number str");
                }
                if (!VariableUtil.tryInt(unit)) {
                    return new ErrorResult("UNIT_ERROR", "The unit must be a number str");
                }
                try {
                    Thread.sleep(Long.parseLong(time) * Long.parseLong(unit));
                } catch (InterruptedException e) {
                    return new ErrorResult("TIME_ERROR", "The time must be a number str");
                }
                break;
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        ArgsSetting argsSetting1 = FunctionUtils.createArgsSetting(
                "String",
                "time" +
                        "\nWait for the specified time",
                "Null");
        argsSetting1.setId("A");
        argsSettings.add(argsSetting1);
        ArgsSetting argsSetting2 = FunctionUtils.createArgsSetting(
                "String String",
                "time unit" +
                        "\nWait for the specified time (time*unit)",
                "Null");
        argsSetting2.setId("B");
        argsSettings.add(argsSetting1);
        argsSettings.add(argsSetting2);
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
