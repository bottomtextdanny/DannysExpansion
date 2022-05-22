package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates.ItemElement;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates.ItemStackPredicate;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates.ItemStackPredicateType;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates.TagElement;
import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Function;

public final class ItemStackPredicateTypes {
    public static final ItemStackPredicateType<?> ITEM = create('\u0000', (tag) -> {
        Item item = Registry.ITEM.byId(tag.getInt("item"));
        if (item != null && item != Items.AIR) {
            return new ItemElement(item);
        } else {
            return null;
        }
    }, (json) -> {
        ResourceLocation rlkey = new ResourceLocation((json.getAsString()));
        if (Registry.ITEM.containsKey(rlkey)) {
            return new ItemElement(Registry.ITEM.get(rlkey));
        } else {
            DannysExpansion.common().logger.error("couldn't read \"{}\" as an item registry", rlkey);
            return null;
        }
    });

    public static final ItemStackPredicateType<?> TAG = create('#', (nbt) -> {
        ResourceLocation rlkey = new ResourceLocation(nbt.getString("item"));
        TagKey<Item> tagKey = TagKey.create(Registry.ITEM_REGISTRY, rlkey);

        return new TagElement(rlkey, Lazy.of(() -> Registry.ITEM.getTag(tagKey).orElseGet(() -> {
            DannysExpansion.common().logger.error("couldn't read \"{}\" as an item registry", rlkey);
            return null;
        })));
    }, (json) -> {
        ResourceLocation rlkey = new ResourceLocation(json.getAsString());
        TagKey<Item> tagKey = TagKey.create(Registry.ITEM_REGISTRY, rlkey);

        return new TagElement(rlkey, Lazy.of(() -> Registry.ITEM.getTag(tagKey).orElseGet(() -> {
            DannysExpansion.common().logger.error("couldn't read \"{}\" as an item registry", rlkey);
            return null;
        })));
    });

    private static <T extends ItemStackPredicate> ItemStackPredicateType<?> create(
            char key,
            Function<CompoundTag, T> tagDeserializer,
            Function<JsonElement, T> jsonDeserializer) {
        return new ItemStackPredicateType<>(key, tagDeserializer, jsonDeserializer);
    }

    public static void loadClass() {}
}
