package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions;

import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.EnchantJson;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class SetEnchantmentsFunction extends LTFunction<SetEnchantmentsFunction> {
    private final LinkedHashSet<EnchantJson> enchantments = new LinkedHashSet<>();

    public SetEnchantmentsFunction(EnchantJson... enchantments) {
        super("minecraft:set_damage");
        this.enchantments.addAll(Arrays.asList(enchantments));
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("enchantments", cObjectCollectionBake(this.enchantments));
    }
}
