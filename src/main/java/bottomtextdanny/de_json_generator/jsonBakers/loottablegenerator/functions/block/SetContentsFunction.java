package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.block;

import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.LTFunction;
import com.google.gson.JsonObject;

public class SetContentsFunction extends LTFunction<SetContentsFunction> {

    public SetContentsFunction() {
        super("minecraft:set_contents");
    }

    @Override
    public void bakeExtra() {
        JsonObject entries = new JsonObject();
        entries.add("type", cString("minecraft:dynamic"));
        entries.add("name", cString("minecraft:contents"));
        this.jsonObj.add("entries", entries);
    }
}
