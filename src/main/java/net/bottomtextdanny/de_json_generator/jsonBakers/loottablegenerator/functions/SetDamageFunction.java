package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions;

import net.bottomtextdanny.de_json_generator.jsonBakers.mojValue.MojValue;

public class SetDamageFunction extends LTFunction<SetDamageFunction> {
    private final MojValue value;

    public SetDamageFunction(MojValue value) {
        super("minecraft:set_damage");
        this.value = value;
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("damage", this.value.get());
    }
}
