package cn.origincraft.magic.function.system.variable.magic;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ArgsSettingResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;

public class ArgsSettingFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "argsSetting";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id=argsSetting.getId();
        switch (id){
            case "A":{
                String argsSettingId= (String) args.get(0).getObject();
                List<?> argsType= (List<?>) args.get(1).getObject();
                List<?> info = (List<?>) args.get(2).getObject();
                List<String> argsTypeStr=new ArrayList<>();
                List<String> infoStr=new ArrayList<>();
                for (Object o : argsType) {
                    argsTypeStr.add(o.toString());
                }
                for (Object o : info) {
                    infoStr.add(o.toString());
                }
                String returnType = (String) args.get(3).getObject();
                ArgsSetting argsSetting1=new ArgsSetting(argsSettingId,argsTypeStr,infoStr,returnType);
                return new ArgsSettingResult(argsSetting1);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("String").addArgType("List").addArgType("List").addArgType("String")
                        .addInfo("id argsType info returnType")
                        .addInfo("Create a argsSetting")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
