package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.interpreter.fastexpression.functions.CallableFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FastFunction;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionParameter;
import cn.origincraft.magic.interpreter.fastexpression.functions.FunctionResult;
import cn.origincraft.magic.interpreter.fastexpression.managers.FunctionManager;
import cn.origincraft.magic.interpreter.fastexpression.parameters.StringParameter;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.object.SpellContextParameter;
import cn.origincraft.magic.object.SpellContextResult;
import cn.origincraft.magic.utils.MethodUtil;
import cn.origincraft.magic.utils.VariableUtil;

import java.util.List;

public class PassFunction implements FastFunction {
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        // 从参数获取上下文
        SpellContext spellContext=
                MethodUtil.getSpellContext(parameter);
        // 从上下文获取当前方法的字符串形式参数
        String para=spellContext.getExecuteParameter();
        // 获取当前环境下的方法管理器
        FunctionManager fManager=spellContext
                .getMagicManager()
                .getFastExpression()
                .getFunctionManager();
        // 解析当前参数 获得参数列表
        List<Object> os= fManager.parseParaExpression(para);
        // 判断参数个数 不满足要求则原样返回上下文
        if (os.size()<1){
            spellContext.putExecuteReturn(new FunctionResult.DefaultResult(FunctionResult.Status.FAILURE));
            return new SpellContextResult(spellContext);
        }
        Object value=os.get(0);
        // 判断参数是否为嵌套方法
        if (MethodUtil.isFunction(value)){
            // 获得方法实例
            CallableFunction callableFunction= (CallableFunction) value;
            // 获得方法的字符串参数
            StringParameter stringParameter=
                    (StringParameter) callableFunction.getParameter();
            // 传入方法的字符串参数
            spellContext.putExecuteParameter(stringParameter.getString());
            // 传入上下文执行方法 获得返回值
            SpellContextResult spellContextResult=
                    (SpellContextResult) callableFunction
                            .getFunction()
                            .call(
                                    new SpellContextParameter(spellContext)
                            );
            spellContext=spellContextResult.getSpellContext();
            FunctionResult functionResult=spellContext.getExecuteReturn();
            // 判断值类型处理
            // Int型
            if (functionResult instanceof FunctionResult.IntResult v){
                spellContext.putExecutePass(v.getInt());
                spellContext.putExecuteReturn(new FunctionResult.DefaultResult(FunctionResult.Status.SUCCESS));
                return new SpellContextResult(spellContext);
            }
            // ...需要处理的结果
        }else {
            // 如果不是嵌套方法 判断是否为变量
            if (spellContext
                    .getVariableMap()
                    .containsKey((String)value)){
                // 提取变量值
                Object v=spellContext.getVariableMap().get((String)value);
                // 存入指定变量名
                if (VariableUtil.tryInt((String) v)){
                    if (Integer.parseInt(String.valueOf(v))>=0){
                        spellContext.putExecutePass(Integer.parseInt((String) v));
                    }
                }
                // 不是变量 默认存为字符串
            }else {
                if (VariableUtil.tryInt((String) value)) {
                    if (Integer.parseInt(String.valueOf(value)) >= 0) {
                        spellContext.putExecutePass(Integer.parseInt((String) value));
                    }
                }
            }
        }
        spellContext.putExecuteReturn(new FunctionResult.DefaultResult(FunctionResult.Status.SUCCESS));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "pass";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
