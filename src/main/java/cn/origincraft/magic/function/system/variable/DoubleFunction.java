package cn.origincraft.magic.function.system.variable;

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
//Double值生成
public class DoubleFunction implements FastFunction {
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
            return new FunctionResult.DoubleResult(0);
        }
        FunctionResult fResult = new FunctionResult.DoubleResult(0);
        // 对象获取
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
            // Double型
            if (functionResult instanceof FunctionResult.DoubleResult){
                fResult=functionResult;
            }
            // Int型
            if (functionResult instanceof FunctionResult.IntResult v){
                fResult=new FunctionResult.DoubleResult(v.getInt());
            }
            // String型
            if (functionResult instanceof FunctionResult.StringResult v){
                if (VariableUtil.tryDouble(v.getString())){
                    fResult=new FunctionResult.DoubleResult(
                            Double.parseDouble(v.getString()));
                }
            }
            // Boolean型
            if (functionResult instanceof FunctionResult.BooleanResult v){
                boolean flag=v.getBoolean();
                if (flag){
                    fResult=new FunctionResult.DoubleResult(1);
                }else {
                    fResult=new FunctionResult.DoubleResult(0);
                }
            }
            // Object型
            if (functionResult instanceof FunctionResult.ObjectResult v){
                if (VariableUtil.isDouble(v.getObject())){
                    fResult=new FunctionResult.DoubleResult((double) v.getObject());
                }
                if (VariableUtil.isInt(v.getObject())){
                    fResult=new FunctionResult.DoubleResult((int) v.getObject());
                }
                if (VariableUtil.isBoolean(v.getObject())){
                    boolean flag= (boolean) v.getObject();
                    if (flag){
                        fResult=new FunctionResult.DoubleResult(1);
                    }else {
                        fResult=new FunctionResult.DoubleResult(0);
                    }
                }
                if (VariableUtil.isString(v.getObject())){
                    if (VariableUtil.tryDouble((String) v.getObject())){
                        fResult=new FunctionResult.DoubleResult(Double.parseDouble((String) v.getObject()));
                    }
                }
            }
            // ...需要处理的结果
        }else {
            // 如果不是嵌套方法 判断是否为变量
            if (spellContext
                    .getVariableMap()
                    .containsKey((String)value)){
                // 提取变量值
                Object v=spellContext.getVariableMap().get((String)value);
                if (VariableUtil.isDouble(v)){
                    fResult=new FunctionResult.DoubleResult((double) v);
                }
                if (VariableUtil.isInt(v)){
                    fResult=new FunctionResult.DoubleResult((int) v);
                }
                if (VariableUtil.isBoolean(v)){
                    boolean flag= (boolean) v;
                    if (flag){
                        fResult=new FunctionResult.DoubleResult(1);
                    }else {
                        fResult=new FunctionResult.DoubleResult(0);
                    }
                }
                if (VariableUtil.isString(v)){
                    if (VariableUtil.tryDouble((String) v)){
                        fResult=new FunctionResult.DoubleResult(Double.parseDouble((String) v));
                    }
                    if (VariableUtil.tryInt((String) v)){
                        fResult=new FunctionResult.DoubleResult(Integer.parseInt((String) v));
                    }
                }
                // 不是变量 默认存为字符串
            }else {
                if (VariableUtil.tryDouble((String) value)){
                    fResult=new FunctionResult.DoubleResult(Double.parseDouble((String) value));
                }
                if (VariableUtil.tryInt((String) value)){
                    fResult=new FunctionResult.DoubleResult(Integer.parseInt((String) value));
                }
            }
        }
        spellContext.putExecuteReturn(fResult);
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "double";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
