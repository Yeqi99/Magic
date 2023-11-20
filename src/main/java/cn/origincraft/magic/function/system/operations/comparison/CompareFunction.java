package cn.origincraft.magic.function.system.operations.comparison;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtils;
import java.util.ArrayList;
import java.util.List;

public class CompareFunction extends ArgsFunction {
    private boolean compareValues(double firstValue, String compareType, double secondValue) {
        try {
            return switch (compareType) {
                case "==" -> Double.compare(firstValue, secondValue) == 0;
                case ">" -> Double.compare(firstValue, secondValue) > 0;
                case ">=" -> Double.compare(firstValue, secondValue) >= 0;
                case "<" -> Double.compare(firstValue, secondValue) < 0;
                case "<=" -> Double.compare(firstValue, secondValue) <= 0;
                case "!=" -> Double.compare(firstValue, secondValue) != 0;
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "compare";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        NumberResult n1= (NumberResult) args.get(0);
        String type = args.get(1).toString();
        NumberResult n2= (NumberResult) args.get(2);
        boolean result = compareValues(n1.toDouble(), type, n2.toDouble());
        return new BooleanResult(result);
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("Number").addArgType("String").addArgType("Number")
                        .addInfo("number type number")
                        .addInfo("return a boolean by compare")
                        .addInfo("type:==,>,<,>=,<=,!=")
                        .setResultType("Boolean")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "CALCULATE";
    }

}
