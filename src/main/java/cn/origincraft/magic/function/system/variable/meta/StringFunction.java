package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class StringFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id=argsSetting.getId();
        switch (id){
            case "A":{
                List<?> list= (List<?>) args.get(0).getObject();
                StringBuilder stringBuilder=new StringBuilder();
                for (Object o : list) {
                    stringBuilder.append(o.toString());
                }
                return new StringResult(stringBuilder.toString());
            }
            case "B":{
                return new StringResult(args.get(0).toString());
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(new ArgsSetting("A")
                .addArgType("List")
                .addInfo("list")
                .addInfo("Convert list to string.")
                .setResultType("String")
        );
        argsSettings.add(new ArgsSetting("B")
                .addArgType(".")
                .addInfo("object")
                .addInfo("Convert object to string.")
                .setResultType("String")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "string";
    }
}
