package cn.origincraft.magic.function.system.info;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class RangeFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "range";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id) {
            case "A": {
                NumberResult end = (NumberResult) args.get(0);
                return ListUtils.generateRange(0, end.toDouble(), 1);
            }
            case "B": {
                NumberResult start = (NumberResult) args.get(0);
                NumberResult end = (NumberResult) args.get(1);
                double startDouble = start.toDouble();
                double endDouble = end.toDouble();
                double step = 1;
                if (startDouble > endDouble) {
                    step = -1;
                }
                return ListUtils.generateRange(startDouble, endDouble, step);
            }
            case "C": {
                NumberResult start = (NumberResult) args.get(0);
                NumberResult end = (NumberResult) args.get(1);
                NumberResult step = (NumberResult) args.get(2);
                return ListUtils.generateRange(start.toDouble(), end.toDouble(), step.toDouble());
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("Number")
                        .addInfo("number")
                        .addInfo("generate a number list")
                        .addInfo("starting from 0 until the specified value step = 1")
                        .addInfo("elements type is double")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("Number").addArgType("Number")
                        .addInfo("start end")
                        .addInfo("generate a number list")
                        .addInfo("starting from start until the specified value step = 1")
                        .addInfo("elements type is double")
                        .setResultType("List")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("Number").addArgType("Number").addArgType("Number")
                        .addInfo("start end step")
                        .addInfo("generate a number list")
                        .addInfo("starting from start until the specified value step = step")
                        .addInfo("elements type is double")
                        .setResultType("List")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
