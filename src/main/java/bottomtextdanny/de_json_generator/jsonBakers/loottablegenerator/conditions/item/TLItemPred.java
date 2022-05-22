package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.item;

import bottomtextdanny.de_json_generator.jsonBakers.JsonDecoder;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.EnchantJson;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

public class TLItemPred extends JsonDecoder {
    private final LinkedHashSet<EnchantJson> enchantments = new LinkedHashSet<>();
    private Optional<String> item = Optional.empty();
    private Optional<Integer> minimumCount = Optional.empty();
    private Optional<Integer> maximumCount = Optional.empty();
    private Optional<Integer> minimumDurability = Optional.empty();
    private Optional<Integer> maximumDurability = Optional.empty();

    public TLItemPred() {
    }

    public TLItemPred item(String item) {
        this.item = Optional.of(item);
        return this;
    }

    public TLItemPred count(int min, int max) {
        this.minimumCount = Optional.of(min);
        this.maximumCount = Optional.of(max);
        return this;
    }

    public TLItemPred durability(int min, int max) {
        this.minimumDurability = Optional.of(min);
        this.maximumDurability = Optional.of(max);
        return this;
    }

    public TLItemPred enchantments(EnchantJson... aEnch) {
        this.enchantments.addAll(Arrays.asList(aEnch));
        return this;
    }

    public TLItemPred bake() {
        if (this.item.isPresent()) this.jsonObj.add("item", cString(this.item.get()));
        if (this.minimumCount.isPresent()) {
            JsonObject countObj = new JsonObject();
            countObj.add("val1", cInt(this.minimumCount.get()));
            countObj.add("val2", cInt(this.maximumCount.get()));
            this.jsonObj.add("count", countObj);
        }

        if (this.minimumDurability.isPresent()) {
            JsonObject countObj = new JsonObject();
            countObj.add("val1", cInt(this.maximumDurability.get()));
            countObj.add("val2", cInt(this.maximumDurability.get()));
            this.jsonObj.add("durability", countObj);
        }

        if (!this.enchantments.isEmpty()) this.jsonObj.add("enchantments", cObjectCollectionBake(this.enchantments));
        return this;
    }
}
