package cn.origincraft.magic.function.system.control;

import cn.origincraft.magic.expression.functions.FunctionResult;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.NormalFunction;
import cn.origincraft.magic.function.results.ErrorResult;
import cn.origincraft.magic.function.results.IntegerResult;
import cn.origincraft.magic.function.results.StringResult;
import cn.origincraft.magic.object.SpellContext;

import java.util.List;

public class ForFunction extends ArgsFunction {
    //TODO
    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args) {
        if (args.size() < 2) {
            return new ErrorResult("INSUFFICIENT_ARGUMENTS", "For function requires at least three arguments.");
        }
        if (args.size() == 2) { // 两个参数时 只有循环变量和终值 默认从0开始 递增1
            // 变量名
            FunctionResult var = args.get(0);
            // 终值
            FunctionResult end = args.get(1);
            // 判断变量名是否为字符串
            if (var instanceof StringResult) {
                // 变量名
                String v = ((StringResult) var).getString();
                // 终值初始化
                int e;
                // 判断终值是否为字符串结果
                if (end instanceof StringResult) {
                    // 终值转换为整数
                    e = Integer.parseInt(((StringResult) end).getString());
                    // 判断终值是否为整数结果
                } else if (end instanceof IntegerResult) {
                    // 终值转换为整数
                    e = ((IntegerResult) end).getInteger();
                } else {
                    // 返回报错信息
                    return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
                }
                // 判断变量是否存在
                if (!spellContext.getContextMap().hasVariable(v)) {
                    spellContext.getContextMap().putVariable(v, 0);
                }
                // 获取循环变量值
                int n = (int) spellContext.getContextMap().getVariable(v);
                // 判断循环变量是否小于终值
                if (n < e) {
                    // 设置下一次执行的位置
                    spellContext.putExecuteNext(spellContext.getExecuteIndex());
                    // 设置循环变量值
                    spellContext.getContextMap().putVariable(v, n + 1);
                    // 返回循环变量值
                } else {
                    // 设置不允许执行下一次
                    spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(), false);
                }
                return new IntegerResult(n);
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
        }
        if (args.size() == 3) {
            // 变量名
            FunctionResult var = args.get(0);
            // 起始值
            FunctionResult start = args.get(1);
            // 终值
            FunctionResult end = args.get(2);
            // 判断变量名是否为字符串
            if (var instanceof StringResult) {
                // 变量名
                String v = ((StringResult) var).getString();
                // 起始值初始化
                int s;
                // 判断起始值是否为字符串结果
                if (start instanceof StringResult) {
                    // 起始值转换为整数
                    s = Integer.parseInt(((StringResult) start).getString());
                    // 判断起始值是否为整数结果
                } else if (start instanceof IntegerResult) {
                    // 起始值转换为整数
                    s = ((IntegerResult) start).getInteger();
                } else {
                    // 返回报错信息
                    return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
                }
                // 终值初始化
                int e;
                // 判断终值是否为字符串结果
                if (end instanceof StringResult) {
                    // 终值转换为整数
                    e = Integer.parseInt(((StringResult) end).getString());
                    // 判断终值是否为整数结果
                } else if (end instanceof IntegerResult) {
                    // 终值转换为整数
                    e = ((IntegerResult) end).getInteger();
                } else {
                    // 返回报错信息
                    return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
                }
                // 判断变量是否存在
                if (!spellContext.getContextMap().hasVariable(v)) {
                    spellContext.getContextMap().putVariable(v, 0);
                }
                // 获取循环变量值
                int n = (int) spellContext.getContextMap().getVariable(v);
                // 判断循环变量是否小于终值
                if (n < e) {
                    // 设置下一次执行的位置
                    spellContext.putExecuteNext(spellContext.getExecuteIndex());
                    // 设置循环变量值
                    if (s < e) {
                        spellContext.getContextMap().putVariable(v, n + 1);
                    } else if (s > e) {
                        spellContext.getContextMap().putVariable(v, n - 1);
                    }
                    // 返回循环
                } else {
                    // 设置不允许执行下一次
                    spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(), false);
                }
                return new IntegerResult(n);
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
        }
        if (args.size() == 4) {
            // 变量名
            FunctionResult var = args.get(0);
            // 起始值
            FunctionResult start = args.get(1);
            // 终值
            FunctionResult end = args.get(2);
            // 步长
            FunctionResult step = args.get(3);
            // 判断变量名是否为字符串
            if (var instanceof StringResult) {
                // 变量名
                String v = ((StringResult) var).getString();
                // 起始值初始化
                int s;
                // 判断起始值是否为字符串结果
                if (start instanceof StringResult) {
                    // 起始值转换为整数
                    s = Integer.parseInt(((StringResult) start).getString());
                    // 判断起始值是否为整数结果
                } else if (start instanceof IntegerResult) {
                    // 起始值转换为整数
                    s = ((IntegerResult) start).getInteger();
                } else {
                    // 返回报错信息
                    return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
                }
                // 终值初始化
                int e;
                // 判断终值是否为字符串结果
                if (end instanceof StringResult) {
                    // 终值转换为整数
                    e = Integer.parseInt(((StringResult) end).getString());
                    // 判断终值是否为整数结果
                } else if (end instanceof IntegerResult) {
                    // 终值转换为整数
                    e = ((IntegerResult) end).getInteger();
                } else {
                    // 返回报错信息
                    return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
                }
                // 步长初始化
                int st;
                // 判断步长是否为字符串结果
                if (step instanceof StringResult) {
                    // 步长转换为整数
                    st = Integer.parseInt(((StringResult) step).getString());
                    // 判断步长是否为整数结果
                } else if (step instanceof IntegerResult) {
                    // 步长转换为整数
                    st = ((IntegerResult) step).getInteger();
                } else {
                    // 返回报错信息
                    return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
                }
                // 判断变量是否存在
                if (!spellContext.getContextMap().hasVariable(v)) {
                    spellContext.getContextMap().putVariable(v, 0);
                }
                // 获取循环变量值
                int n = (int) spellContext.getContextMap().getVariable(v);
                // 判断循环变量是否小于终值
                if (n < e) {
                    // 设置下一次执行的位置
                    spellContext.putExecuteNext(spellContext.getExecuteIndex());
                    // 设置循环变量值
                    if (s < e) {
                        spellContext.getContextMap().putVariable(v, n + st);
                    } else if (s > e) {
                        spellContext.getContextMap().putVariable(v, n - st);
                    }
                    // 返回循环
                } else {
                    // 设置不允许执行下一次
                    spellContext.putExecuteIndexAllow(spellContext.getExecuteIndex(), false);
                }
                return new IntegerResult(n);
            } else {
                return new ErrorResult("UNKNOWN_ARGUMENT_TYPE", "Unsupported argument type.");
            }
        }
        return null;
    }

    @Override
    public FunctionResult whenFunctionCalled(SpellContext spellContext, List<FunctionResult> args, ArgsSetting argsSetting) {
        return null;
    }

    @Override
    public List<ArgsSetting> getArgsSetting() {
        return null;
    }

    @Override
    public String getType() {
        return "SYSTEM";
    }

    @Override
    public String getName() {
        return "for";
    }
}
