package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions;

import net.bottomtextdanny.de_json_generator.jsonBakers.mojValue.MojValue;

public class EnchantWithLevelsFunction extends LTFunction<EnchantWithLevelsFunction> {
    private final MojValue levels;
    private boolean treasure;

    public EnchantWithLevelsFunction(MojValue levels) {
        super("enchant_with_levels");
        this.levels = levels;
    }

    public EnchantWithLevelsFunction treasure() {
        this.treasure = true;
        return this;
    }


    @Override
    public void bakeExtra() {
        this.jsonObj.add("treasure", cBool(this.treasure));
        this.jsonObj.add("levels", this.levels.get());
    }
}
