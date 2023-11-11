package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.ArrayList;
import java.util.List;

public class BooleanFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id=argsSetting.getId();
        switch (id){
            case "A":{
                String str= (String) args.get(0).getObject();
                return new BooleanResult(Boolean.parseBoolean(str));
            }
            case "B":{
                NumberResult numberResult= (NumberResult) args.get(0);
                return new BooleanResult(numberResult.toBoolean());
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(new ArgsSetting("A")
                .addArgType("String")
                        .addInfo("string")
                        .addInfo("Convert string to boolean.")
                .setResultType("Boolean")
        );
        argsSettings.add(new ArgsSetting("B")
                .addArgType("Number")
                        .addInfo("number")
                        .addInfo("Convert number to boolean.")
                .setResultType("Boolean")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "boolean";
    }
}
