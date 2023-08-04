package cn.origincraft.magic;

import cn.origincraft.magic.interpreter.fastexpression.FastExpression;

public class MagicManager {
    private static FastExpression fastExpression;
    public static void init(){
        fastExpression=new FastExpression();
    }
    public static boolean isInit(){
        return fastExpression != null;
    }

    public static FastExpression getFastExpression() {
        return fastExpression;
    }

    public static void setFastExpression(FastExpression fastExpression) {
        MagicManager.fastExpression = fastExpression;
    }
}
