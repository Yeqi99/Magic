package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.BooleanResult;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.object.SpellContext;

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
            case "C":{
                Object object = args.get(0).getObject();
                if(!(object instanceof Boolean)){
                    return new ErrorResult("TYPE_ERROR","Object convert to boolean failed");
                }
                return new BooleanResult((Boolean) object);
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
        argsSettings.add(new ArgsSetting("C")
                .addArgType(".")
                .addInfo("object")
                .addInfo("Convert object to boolean")
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
