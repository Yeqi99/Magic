package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.List;
//TODO
public class RangeFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "range";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id =argsSetting.getId();
        switch (id){
            case "A":{
                NumberResult numberResult= (NumberResult) args.get(0);
                if (numberResult.isInteger()){

                }
            }
        }
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings = new ArrayList<>();
        argsSettings.add(
          new ArgsSetting("A")
                  .addArgType("Number")
                  .addInfo("number")
                  .addInfo("generate a number list")
                  .addInfo("starting from 0 until the specified value")
                  .setResultType("List")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
