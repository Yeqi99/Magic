package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.*;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.ArrayList;
import java.util.List;

public class NumberFunction extends ArgsFunction {


    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id=argsSetting.getId();
        switch (id){
            case "A":{
                String str= (String) args.get(0).getObject();
                return new NumberResult(str);
            }
            case "B":{
                String str= (String) args.get(0).getObject();
                String type= (String) args.get(1).getObject();
                return new NumberResult(VariableUtil.stringToNumber(str,type,0));
            }
            case "C":{
                String str= (String) args.get(0).getObject();
                String type= (String) args.get(1).getObject();
                Number defaultValue= (Number) args.get(2).getObject();
                return new NumberResult(VariableUtil.stringToNumber(str,type,defaultValue));
            }
            case "D":{
                NumberResult numberResult= (NumberResult) args.get(0);
                return new StringResult(numberResult.getNumberType());
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(new ArgsSetting("A")
                .addArgType("String")
                        .addInfo("stringNumber")
                        .addInfo("Convert string to number.")
                .setResultType("Number")
        );
        argsSettings.add(new ArgsSetting("B")
                .addArgType("String").addArgType("String")
                        .addInfo("stringNumber type")
                        .addInfo("Convert string to number by type.")
                        .addInfo("type: int, double, float, long")
                .setResultType("Number")
        );
        argsSettings.add(new ArgsSetting("C")
                .addArgType("String").addArgType("String").addArgType("Number")
                .addInfo("stringNumber type defaultValue")
                .addInfo("Convert string to number by type.")
                .addInfo("type: int, double, float, long")
                .setResultType("Number")
        );
        argsSettings.add(new ArgsSetting("D")
                .addArgType("Number")
                .addInfo("number")
                .addInfo("Get number's type.")
                .setResultType("String")
        );
        return argsSettings;
    }
    @Override
    public String getName() {
        return "number";
    }
    @Override
    public String getType() {
        return "SYSTEM";
    }
}
