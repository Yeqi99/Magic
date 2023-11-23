package cn.origincraft.magic.function.system.util.list;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.NullResult;
import cn.origincraft.magic.function.results.NumberResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortFunction extends ArgsFunction {
    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        String id = argsSetting.getId();
        switch (id){
            case "A":{
                List<?> list= (List<?>) args.get(0).getObject();
                List<Double> dList=new ArrayList<>();
                String way=args.get(0).toString();
                for (Object object : list) {
                    NumberResult numberResult=null;
                    if(!(object instanceof Number)){
                        return new ErrorResult("TYPE_ERROR","list elements must be number");
                    }
                    numberResult=new NumberResult((Number) object);
                    dList.add(numberResult.toDouble());
                }
                if (way.startsWith("desc")){
                    dList.sort(Collections.reverseOrder());
                }else {
                    Collections.sort(dList);
                }
                return new ListResult(dList);
            }
        }
        return new NullResult();
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        List<ArgsSetting> argsSettings=new ArrayList<>();
        argsSettings.add(
                new ArgsSetting("A")
                        .addArgType("List").addArgType("String")
                        .addInfo("list way")
                        .addInfo("sort the list in the specified way")
                        .addInfo("type:asc(ascending order),desc(descending order)")
                        .setResultType("List")
        );
        return argsSettings;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
