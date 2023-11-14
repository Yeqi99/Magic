package cn.origincraft.magic.function.results;

import cn.origincraft.magic.function.ArgsSetting;

public class ArgsSettingResult extends ObjectResult{
    public ArgsSettingResult(ArgsSetting argsSetting) {
        super(argsSetting);
    }

    public ArgsSetting getArgsSetting() {
        return (ArgsSetting) getObject();
    }
    @Override
    public String getName() {
        return "ArgsSetting";
    }
    public String toString(){
        return getArgsSetting().getId();
    }
}
