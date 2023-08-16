package cn.origincraft.magic.function.system.in;

import cn.origincraft.magic.MagicManager;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import dev.rgbmc.expression.functions.CallableFunction;
import dev.rgbmc.expression.functions.FastFunction;
import dev.rgbmc.expression.functions.FunctionParameter;
import dev.rgbmc.expression.functions.FunctionResult;
import dev.rgbmc.expression.parameters.StringParameter;
import java.util.List;
import java.util.Scanner;

public class InputFunction implements FastFunction {

    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String prompt = spellContext.getExecuteParameter();
        MagicManager mm = spellContext.getMagicManager();
        List<Object> list = mm
                .getFastExpression()
                .getFunctionManager()
                .parseParaExpression(prompt);
        StringBuilder s = new StringBuilder();
        for (Object o : list) {
            if (MethodUtil.isFunction(o)){
                CallableFunction function = (CallableFunction) o;
                StringParameter stringParameter = (StringParameter)function.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextResult spellContextResult =
                        (SpellContextResult) function
                                .getFunction()
                                .call(
                                        new SpellContextParameter(spellContext)
                                );
                spellContext = spellContextResult.getSpellContext();
                FunctionResult functionResult = spellContext.getExecuteReturn();
                if (functionResult instanceof FunctionResult.StringResult v){
                    s.append(v.getString());
                }
            } else {
                String str = (String) o;
                s.append(spellContext.getVariableMap().getOrDefault(str, str));
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(s);
        String input = scanner.nextLine();
        spellContext.putExecuteReturn(new FunctionResult.StringResult(input));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "input";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}