package bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates;

import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.ItemStackPredicateTypes;
import com.google.gson.JsonElement;
import bottomtextdanny.braincell.Braincell;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ItemStackPredicateType<T extends ItemStackPredicate> {
    private static final Map<Character, ItemStackPredicateType<?>> KEY_TO_PREDICATE =
            new HashMap<>();
    private static final List<ItemStackPredicateType<?>> PREDICATE_LIST = new ArrayList<>();
    private static int idCounter = 0;
    private final int id;
    private final char key;
    private final Function<CompoundTag, T> tagDeserializer;
    private final Function<JsonElement, T> jsonDeserializer;

    public ItemStackPredicateType(char key, Function<CompoundTag, T> tagDeserializer, Function<JsonElement, T> jsonDeserializer) {
        super();
        if (Braincell.common().hasPassedInitialization())
            throw new UnsupportedOperationException("Cannot add more itemstack predicates after mod loading phase.");
        this.id = idCounter;
        idCounter++;
        this.key = key;
        PREDICATE_LIST.add(this);
        KEY_TO_PREDICATE.put(key, this);
        this.tagDeserializer = tagDeserializer;
        this.jsonDeserializer = jsonDeserializer;
    }

    public Function<CompoundTag, T> getTagDeserializer() {
        return tagDeserializer;
    }

    public Function<JsonElement, T> getJsonDeserializer() {
        return jsonDeserializer;
    }

    public int getId() {
        return id;
    }

    public char getKey() {
        return key;
    }

    public static ItemStackPredicateType<?> getById(int id) {
        return PREDICATE_LIST.get(id);
    }

    public static ItemStackPredicateType<?> getByKey(char key) {
        return KEY_TO_PREDICATE.getOrDefault(key, ItemStackPredicateTypes.ITEM);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStackPredicateType<?> that = (ItemStackPredicateType<?>) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
