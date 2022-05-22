package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import com.google.gson.JsonObject;

import java.util.Optional;

public class EnchantJson extends JsonBaker<EnchantJson> {
    private final String enchantmentID;
    private Optional<Integer> minLevel = Optional.empty(), maxLevel = Optional.empty();

    public EnchantJson(String enchantmentID) {
        this.enchantmentID = enchantmentID;
    }

    public EnchantJson bounds(int min, int max) {
        this.minLevel = Optional.of(min);
        this.maxLevel = Optional.of(max);
        return this;
    }

    public EnchantJson min(int min) {
        this.minLevel = Optional.of(min);
        return this;
    }

    @Override
    public EnchantJson bake() {
        JsonObject levels = new JsonObject();
	    boolean validateLevelObj = false;

        this.jsonObj.add("enchantment", cString(this.enchantmentID));
        
        if (this.minLevel.isPresent()) {
	        validateLevelObj = true;
	        levels.add("val1", cInt(this.minLevel.get()));
        }
        if (this.maxLevel.isPresent()) {
        	validateLevelObj = true;
	        levels.add("val2", cInt(this.maxLevel.get()));
        }

        if (validateLevelObj) this.jsonObj.add("levels", levels);
        return this;
    }
}
