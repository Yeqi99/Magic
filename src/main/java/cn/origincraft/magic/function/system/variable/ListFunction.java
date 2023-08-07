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

import java.util.ArrayList;
import java.util.List;

public class ListFunction implements FastFunction {

    /**
     * 根据参数生成一个List<Object>存入SpellContext中
     * 生成的List<Object>的长度为参数个数
     * 生成的List<Object>的每个元素为参数的值
     * 注意：参数的值为Object类型，可能为任意类型
     * 注意：参数可能是另一个方法的嵌套
     * 注意：参数可能是变量
     * 注意：参数可能是常量
     * 如果是方法的嵌套，需要将嵌套方法的返回值作为参数的值
     * 如果是变量，需要将变量的值作为参数的值
     * 如果是常量，需要将常量的值作为参数的值
     * 如果参数个数为0，则返回一个空的List<Object>
     * 函数是覆写父类的call方法
      * @param parameter 参数
     * @return 返回值是SpellContextResult类型，其中包含了SpellContext 并且SpellContext中的executeReturn是一个List<Object>
     */
    @Override
    public FunctionResult call(FunctionParameter parameter) {
        SpellContext spellContext = MethodUtil.getSpellContext(parameter);
        String para = spellContext.getExecuteParameter();
        FunctionManager fManager = spellContext
                .getMagicManager()
                .getFastExpression()
                .getFunctionManager();
        List<Object> os = fManager.parseParaExpression(para);

        List<Object> resultList = new ArrayList<>();

        for (Object value : os) {
            if (MethodUtil.isFunction(value)) {
                CallableFunction callableFunction = (CallableFunction) value;
                StringParameter stringParameter = (StringParameter) callableFunction.getParameter();
                spellContext.putExecuteParameter(stringParameter.getString());
                SpellContextParameter spellContextParameter = new SpellContextParameter(spellContext);
                SpellContextResult spellContextResult = (SpellContextResult) callableFunction.getFunction().call(spellContextParameter);
                spellContext=spellContextResult.getSpellContext();
                FunctionResult functionResult=spellContextResult.getSpellContext().getExecuteReturn();
                if (functionResult instanceof FunctionResult.ListResult) {
                    resultList.add(((FunctionResult.ListResult) functionResult).getList());
                }
                if (functionResult instanceof FunctionResult.ObjectResult) {
                    resultList.add(((FunctionResult.ObjectResult) functionResult).getObject());
                }
                if (functionResult instanceof FunctionResult.StringResult) {
                    resultList.add(((FunctionResult.StringResult) functionResult).getString());
                }
                if (functionResult instanceof FunctionResult.IntResult) {
                    resultList.add(((FunctionResult.IntResult) functionResult).getInt());
                }
                if (functionResult instanceof FunctionResult.DoubleResult) {
                    resultList.add(((FunctionResult.DoubleResult) functionResult).getDouble());
                }
                if (functionResult instanceof FunctionResult.BooleanResult) {
                    resultList.add(((FunctionResult.BooleanResult) functionResult).getBoolean());
                }
                if (functionResult instanceof FunctionResult.DefaultResult v) {
                    if (v.getStatus()==FunctionResult.Status.SUCCESS){
                        resultList.add(true);
                    }else if (v.getStatus()==FunctionResult.Status.FAILURE) {
                        resultList.add(false);
                    }else {
                        resultList.add(null);
                    }
                }
            } else if (spellContext.getVariableMap().containsKey(value.toString())) {
                Object v = spellContext.getVariableMap().get(value.toString());
                resultList.add(v);
            } else {
                resultList.add(value);
            }
        }

        spellContext.putExecuteReturn(new FunctionResult.ListResult(resultList));
        return new SpellContextResult(spellContext);
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }
}
