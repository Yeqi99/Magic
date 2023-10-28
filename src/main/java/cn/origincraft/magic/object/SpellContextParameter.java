package cn.origincraft.magic.object;


import cn.origincraft.magic.expression.functions.FunctionParameter;

public class SpellContextParameter extends FunctionParameter {

    public SpellContextParameter(SpellContext spellContext){
        setObject(spellContext);
    }
    public SpellContext getSpellContext(){
        return (SpellContext) getObject();
    }
    public void setSpellContext(SpellContext spellContext){
        setObject(spellContext);
    }

}
