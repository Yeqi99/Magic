package cn.origincraft.magic.interpreter.fastexpression;


import cn.origincraft.magic.interpreter.fastexpression.managers.AliasesManager;
import cn.origincraft.magic.interpreter.fastexpression.managers.FunctionManager;

public class FastExpression {
    private final FunctionManager functionManager;
    private final AliasesManager aliasesManager;

    public FastExpression() {
        functionManager = new FunctionManager(this);
        aliasesManager = new AliasesManager();
    }

    public FunctionManager getFunctionManager() {
        return functionManager;
    }

    public AliasesManager getAliasesManager() {
        return aliasesManager;
    }
}