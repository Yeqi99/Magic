package cn.origincraft.magic;

import cn.origincraft.magic.function.FunctionRegister;
import dev.rgbmc.expression.FastExpression;
import java.util.HashMap;
import java.util.Map;

public class MagicManager {
    private FastExpression fastExpression;
    // 行为优先级
    private Map<String,Integer> typePriority=new HashMap<>();
    // 目标优先级
    private int targetPriority=20;
    // 行为优先级
    private int behaviorPriority=10;
    // 约束优先级
    private int constraintPriority=30;
    public MagicManager(){
        init();
    }
//    public List<String> getFunctionsRealNames(){
//        return getFastExpression().getFunctionManager().getFunctionRealNames();
//    }

    public void init(){
        fastExpression=new FastExpression();
        getTypePriority().put("TARGET", targetPriority);
        getTypePriority().put("BEHAVIOR",behaviorPriority);
        getTypePriority().put("CONSTRAINT",constraintPriority);
        FunctionRegister.regDefault(this);
    }
    public boolean isInit(){
        return fastExpression != null;
    }

//    public boolean addAlias(String realName, String alias) {
//        return getFastExpression().getAliasesManager().addAlias(realName, alias);
//    }

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
}
