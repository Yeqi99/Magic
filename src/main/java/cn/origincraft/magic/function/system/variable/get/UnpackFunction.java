package cn.origincraft.magic.function.system.variable.get;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ArgumentsResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class UnpackFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "unpack";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id){
            case "A":{
                List<FunctionResult> arguments= (List<FunctionResult>) args.get(0).getObject();
                String name = (String) args.get(1).getObject();
                for(int i=0;i<arguments.size();i++){
                    spellContext.getContextMap().putVariable(name + (i + 1), arguments.get(i));
                }
                return new ArgumentsResult(arguments);
            }
            case "B":{
                List<?> list = (List<?>) args.get(0).getObject();
                String name = (String) args.get(1).getObject();
                for(int i=0;i<list.size();i++){
                    spellContext.getContextMap().putVariable(name + (i + 1), list.get(i));
                }
                return new ListResult(list);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("Arguments").addArgType("String")
                        .addInfo("args variableName")
                        .addInfo("unpack arguments to variable")
                        .setResultType("Arguments")
        );
        argsSettings.add(
                new ArgsSetting("B")
                        .addArgType("List").addArgType("String")
                        .addInfo("list variableName")
                        .addInfo("unpack list to variable")
                        .setResultType("List")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
