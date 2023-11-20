package cn.origincraft.magic.function.system.variable.meta;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.VariableUtils;

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
                return new NumberResult(VariableUtils.stringToNumber(str,type,0));
            }
            case "C":{
                String str= (String) args.get(0).getObject();
                String type= (String) args.get(1).getObject();
                Number defaultValue= (Number) args.get(2).getObject();
                return new NumberResult(VariableUtils.stringToNumber(str,type,defaultValue));
            }
            case "D":{
                NumberResult numberResult= (NumberResult) args.get(0);
                return new StringResult(numberResult.getNumberType());
            }
            case "E":{
                NumberResult numberResult= (NumberResult) args.get(0);
                String type=args.get(1).toString();
                if (type.equalsIgnoreCase("int")){
                    return new NumberResult(numberResult.toInteger());
                }else if(type.equalsIgnoreCase("long")){
                    return new NumberResult(numberResult.toLong());
                }else if(type.equalsIgnoreCase("float")){
                    return new NumberResult(numberResult.toFloat());
                }else if(type.equalsIgnoreCase("double")){
                    return new NumberResult(numberResult.toDouble());
                }else {
                    return numberResult;
                }
            }
            case "F":{
                Object object=args.get(0).getObject();
                if (!(object instanceof Number)){
                    return new ErrorResult("TYPE_ERROR","Object convert to number failed");
                }
                return new NumberResult((Number) object);
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
        argsSettings.add(new ArgsSetting("E")
                .addArgType("Number").addArgType("String")
                .addInfo("number type")
                .addInfo("Change number's type.")
                .addInfo("type: int, double, float, long")
                .setResultType("Number")
        );
        argsSettings.add(new ArgsSetting("F")
                .addArgType(".")
                .addInfo("object")
                .addInfo("Convert object to number")
                .setResultType("Number")
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
