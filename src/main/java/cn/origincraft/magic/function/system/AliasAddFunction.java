package cn.origincraft.magic.function.system;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.ParseType;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.FunctionUtils;

import java.util.ArrayList;
import java.util.List;

public class AliasAddFunction extends ArgsFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id=argsSetting.getId();
        switch (id){
            case "A":{
                String real=args.get(0).toString();
                List<FunctionResult> aliases=args.subList(1,args.size());
                for (FunctionResult alias : aliases) {
                    if (alias instanceof StringResult){
                        String aliasString = alias.toString();
                        spellContext.getMagicManager().addAlias(real, aliasString);
                    }
                }
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        setParseType(0, ParseType.ONLY_FUNCTION);
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("String").addArgType("...")
                        .addInfo("string ...(string)")
                        .addInfo("real alias...")
                        .addInfo("Add alias to real.")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "aliasAdd";
    }
}
