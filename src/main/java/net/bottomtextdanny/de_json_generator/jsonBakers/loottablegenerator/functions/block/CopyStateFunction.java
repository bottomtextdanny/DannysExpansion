package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.block;

import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.LTFunction;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class CopyStateFunction extends LTFunction<CopyStateFunction> {
    private final LinkedHashSet<String> properties = new LinkedHashSet<>(0);
    private final String blockID;

    public CopyStateFunction(String blockID) {
        super("minecraft:copy_state");
        this.blockID = blockID;
    }

    public CopyStateFunction properties(String... aStr) {
        this.properties.addAll(Arrays.asList(aStr));
        return this;
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("block", cString(this.blockID));
        this.jsonObj.add("properties", cStringCollection(this.properties));
    }
}
