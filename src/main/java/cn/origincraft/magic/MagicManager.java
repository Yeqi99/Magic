package cn.origincraft.magic;

import cn.origincraft.magic.expression.FastExpression;
import cn.origincraft.magic.expression.functions.FastFunction;
import cn.origincraft.magic.function.ArgsFunction;
import cn.origincraft.magic.function.ArgsSetting;
import cn.origincraft.magic.function.FunctionRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MagicManager {
    private FastExpression fastExpression;
    // 行为优先级
    private Map<String, Integer> typePriority = new HashMap<>();
    // 目标优先级
    private int targetPriority = 20;
    // 行为优先级
    private int behaviorPriority = 10;
    // 约束优先级
    private int constraintPriority = 30;

    public MagicManager() {
        init();
    }

    public void registerDefaultFunction() {
        FunctionRegister.regDefault(this);
    }

    public void init() {
        fastExpression = new FastExpression();
        getTypePriority().put("TARGET", targetPriority);
        getTypePriority().put("BEHAVIOR", behaviorPriority);
        getTypePriority().put("CONSTRAINT", constraintPriority);
    }

    public boolean isInit() {
        return fastExpression != null;
    }

    public boolean addAlias(String realName, String alias) {
        if (!getFastExpression().getAliasesManager().getRealName(alias).equals(alias)) {
            return false;
        }
        getFastExpression().getAliasesManager().addAlias(realName, alias, getFastExpression().getFunctionManager());
        return true;
    }

    public Set<String> getFunctionsRealNames() {
        return getFastExpression().getFunctionManager().getRegistry().keySet();
    }

    public void registerFunction(FastFunction fastFunction, String... alies) {
        getFastExpression().getFunctionManager().register(fastFunction, alies);
    }

    public void registerFunction(FastFunction fastFunction) {
        getFastExpression().getFunctionManager().register(fastFunction);
    }

    public FastExpression getFastExpression() {
        return fastExpression;
    }

    public void setFastExpression(FastExpression fastExpression) {
        this.fastExpression = fastExpression;
    }

    public Map<String, Integer> getTypePriority() {
        return typePriority;
    }

    public void setTypePriority(Map<String, Integer> typePriority) {
        this.typePriority = typePriority;
    }

    public int getTargetPriority() {
        return targetPriority;
    }

    public void setTargetPriority(int targetPriority) {
        this.targetPriority = targetPriority;
    }

    public int getBehaviorPriority() {
        return behaviorPriority;
    }

    public void setBehaviorPriority(int behaviorPriority) {
        this.behaviorPriority = behaviorPriority;
    }

    public int getConstraintPriority() {
        return constraintPriority;
    }

    public void setConstraintPriority(int constraintPriority) {
        this.constraintPriority = constraintPriority;
    }

    public String getUsagePrompt(){
        List<FastFunction>  fastExpressions =fastExpression.getFunctionManager().getFunctions();
        StringBuilder prompt= new StringBuilder();
        for (FastFunction fastFunction:fastExpressions) {

            Map<String, List<String>> aliasesMap = fastExpression.getAliasesManager().getAliases();
            if (aliasesMap.containsKey(fastFunction.getName())){
                StringBuilder aliases = new StringBuilder();
                for (String s : getFastExpression().getAliasesManager().getAliases().get(fastFunction.getName())) {
                    aliases.append(s).append(" ");
                }
                prompt.append("[").append(fastFunction.getName()).append("(").append(aliases).append(")").append("]{\n");
            }else {
                prompt.append("[").append(fastFunction.getName()).append("]{\n");
            }
            if (fastFunction instanceof ArgsFunction argsFunction) {
                for (String s : argsFunction.getUsage()) {
                   prompt.append("    ").append(s).append("\n");
                }
                prompt.append("}\n");
            }else {
                prompt.append("]{\nUNKNOWN\n}\n");
            }
        }
        return prompt.toString();
    }
}
