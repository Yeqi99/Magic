package cn.origincraft.magic.function.system.out;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.function.results.ListResult;
import cn.origincraft.magic.function.results.MapResult;
import cn.origincraft.magic.function.results.SetResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.FunctionUtils;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.parameters.StringParameter;
import dev.rgbmc.expression.results.*;
import java.util.List;

public class PrintFunction implements FastFunction {

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext= MethodUtil.getSpellContext(parameter);
        String para=spellContext.getExecuteParameter();
        MagicManager mm=spellContext.getMagicManager();
        List<Object> list= FunctionUtils.parseParaExpression(para,
                        mm
                        .getFastExpression()
                        .getFunctionManager());
        StringBuilder s= new StringBuilder();
        for (Object o : list) {
            if (MethodUtil.isFunction(o)){
                CallableFunction function= (CallableFunction) o;
                StringParameter stringParameter=
                        (StringParameter)function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextResult spellContextResult =
                        (SpellContextResult) function
                                .getFunction()
                                .call(
                                        new SpellContextParameter(spellContext)
                                );
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContext.getExecuteReturn();
                // 判断值类型处理
                // Double型
                if (functionResult instanceof DoubleResult v){
                    s.append(v.getDouble());
                }
                // Int型
                if (functionResult instanceof IntegerResult v){
                    s.append(v.getInteger());
                }
                // String型
                if (functionResult instanceof StringResult v){
                    s.append(v.getString());
                }
                // Object型
                if (functionResult instanceof ObjectResult v){
                    s.append(v.getObject());
                }
                // Boolean型
                if (functionResult instanceof BooleanResult v){
                    s.append(v.getBoolean());
                }
                // Map型
                if (functionResult instanceof MapResult v){
                    s.append(v.getMap());
                }
                // List型
                if (functionResult instanceof FunctionResult.DefaultResult v){
                    if (v.getStatus()== FunctionResult.Status.SUCCESS){
                        s.append(true);
                    }else if (v.getStatus()== FunctionResult.Status.FAILURE){
                        s.append(false);
                    }else {
                        s.append("null");
                    }
                }
                // List型
                if (functionResult instanceof ListResult v){
                    s.append(v.getList());
                }
                // Set型
                if (functionResult instanceof SetResult v){
                    s.append(v.getSet());
                }
            }else {
                String str= (String) o;
                s.append(spellContext.getVariableMap().getOrDefault(str, str));
            }
        }

        if (!s.isEmpty()){
            System.out.print(s);
        }
        spellContext.putExecuteReturn(new FunctionResult.DefaultResult(FunctionResult.Status.SUCCESS));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "print";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
