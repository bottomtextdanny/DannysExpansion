package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.block;

import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.LTFunction;

public class CopyNameFunction extends LTFunction<CopyNameFunction> {

    public CopyNameFunction() {
        super("copy_name");
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("source", cString("block_entity"));
    }
}
