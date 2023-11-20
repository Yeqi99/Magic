package cn.origincraft.magic.function.system.control.execute;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ContextMapResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.ContextMap;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class LoadContextFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        switch (id){
            case "A":{
                ContextMap contextMap= (ContextMap) args.get(0).getObject();
                spellContext.importContextMap(contextMap);
                return new ContextMapResult(spellContext.getContextMap());
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("ContextMap")
                        .addInfo("contextMap")
                        .addInfo("load a contextMap to now spellContext")
                        .setResultType("ContextMap")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "loadContext";
    }
}
