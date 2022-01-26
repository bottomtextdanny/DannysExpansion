package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions;

import net.bottomtextdanny.de_json_generator.jsonBakers.mojValue.MojValue;

public class SetCountFunction extends LTFunction<SetCountFunction> {
    private final MojValue value;

    public SetCountFunction(MojValue value) {
        super("minecraft:set_count");
        this.value = value;
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("count", this.value.get());
    }
}
