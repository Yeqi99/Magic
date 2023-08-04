package cn.origincraft.magic;

import cn.origincraft.magic.interpreter.fastexpression.FastExpression;

import java.util.HashMap;
import java.util.Map;

public class MagicManager {
    private FastExpression fastExpression;
    private Map<String,Integer> typePriority=new HashMap<>();
    private int targetPriority=20;
    private int behaviorPriority=10;
    private int constraintPriority=30;
    public MagicManager(){
        init();
    }

    public void init(){
        fastExpression=new FastExpression();
        getTypePriority().put("TARGET", targetPriority);
        getTypePriority().put("BEHAVIOR",behaviorPriority);
        getTypePriority().put("CONSTRAINT",constraintPriority);
    }
    public boolean isInit(){
        return fastExpression != null;
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
}
