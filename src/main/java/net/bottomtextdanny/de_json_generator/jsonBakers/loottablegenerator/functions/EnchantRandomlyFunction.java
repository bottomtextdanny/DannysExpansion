package net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class EnchantRandomlyFunction extends LTFunction<EnchantRandomlyFunction> {
    private final LinkedHashSet<String> enchantmentIDs = new LinkedHashSet<>(0);

    public EnchantRandomlyFunction(String... aEnchantments) {
        super("enchant_randomly");
        this.enchantmentIDs.addAll(Arrays.asList(aEnchantments));
    }

    @Override
    public void bakeExtra() {
        this.jsonObj.add("enchantments", cStringCollection(this.enchantmentIDs));
    }
}
