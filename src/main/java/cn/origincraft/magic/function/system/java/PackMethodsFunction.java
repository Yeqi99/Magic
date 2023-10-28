package cn.origincraft.magic.function.system.java;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.ObjectResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;
import cn.origincraft.magic.utils.ResultUtils;

import java.lang.reflect.Method;
import java.util.List;

public class PackMethodsFunction extends NormalFunction {
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments don't have enough args.");
        }
        FunctionResult nameResult = args.get(0);
        FunctionResult methodNameResult = args.get(1);
        if (!(nameResult instanceof StringResult)) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        if (!(methodNameResult instanceof StringResult)) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        String name = ((StringResult) nameResult).getString();
        String methodName = ((StringResult) methodNameResult).getString();
        if (!spellContext.getContextMap().hasVariable(name + ".o")) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        if (!spellContext.getContextMap().hasVariable(name + ".m." + methodName)) {
            return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
        }
        Object obj = spellContext.getContextMap().getVariable(name + ".o");
        Method method = (Method) spellContext.getContextMap().getVariable(name + ".m." + methodName);
        if (args.size() > 2) {
            List<FunctionResult> methodArg = args.subList(2, args.size());
            Object[] methodArgs = ResultUtils.reductionToArray(methodArg);
            try {
                Object result = method.invoke(obj, methodArgs);
                return new ObjectResult(result);
            } catch (Exception e) {
                return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
            }
        } else {
            try {
                Object result = method.invoke(obj);
                return new ObjectResult(result);
            } catch (Exception e) {
                return new ErrorResult("ARGUMENTS_FUNCTION_ARGS_ERROR", "Arguments type error.");
            }
        }
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "packMethods";
    }
}
