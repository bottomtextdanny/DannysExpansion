package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe;

import com.google.gson.JsonSyntaxException;
import net.bottomtextdanny.dannys_expansion.core.misc.JsonReadUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class LazyDeserializers {
	public static final Map<Integer, LazyIngredientDeserializer> DESERIALIZATION_BY_INT_IDENTIFIER = new HashMap<>();
	public static final Map<String, LazyIngredientDeserializer> DESERIALIZATION_BY_STR_IDENTIFIER = new HashMap<>();
	private static int numberIdentifier;
	
	public static final LazyIngredientDeserializer ITEM_DESERIALIZER = add(new LazyIngredientDeserializer(numberIdentifier++, "item",
			compound -> new ItemIngredient(Item.byId(compound.getInt("item")), compound.getInt("count")),
			jsonObj ->
			{
				Optional<Item> mabyItem = Registry.ITEM.getOptional(new ResourceLocation(JsonReadUtil.getString(jsonObj, "item")));
				if (mabyItem.isPresent()) {
					return new ItemIngredient(mabyItem.get(), JsonReadUtil.getInt(jsonObj, "count"));
				}
				return null;
			}
	));
	
	public static final LazyIngredientDeserializer TAG_DESERIALIZER = add(new LazyIngredientDeserializer(numberIdentifier++, "tag",
            compound ->
			{
				Tag<Item> uncheckedTag = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, new ResourceLocation(compound.getString("tag")), p_151262_ -> {
				return new JsonSyntaxException("Unknown item tag '" + p_151262_ + "'");
				});
				if (uncheckedTag != null) {
					return new TagIngredient(uncheckedTag, compound.getInt("count"));
				}

				return TagIngredient.BROKEN.get();
				},
            jsonObj ->
			{
			Tag<Item> uncheckedTag = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, new ResourceLocation(JsonReadUtil.getString(jsonObj, "tag")), p_151262_ -> {
				return new JsonSyntaxException("Unknown item tag '" + p_151262_ + "'");
			});
			
			if (uncheckedTag != null) {
				return new TagIngredient(uncheckedTag, JsonReadUtil.getInt(jsonObj, "count"));
			}
			
			return TagIngredient.BROKEN.get();
			}
	));

	public static final LazyIngredientDeserializer TOOL_DESERIALIZER = add(new LazyIngredientDeserializer(numberIdentifier++, "tool",
			compound -> new ToolIngredient(Item.byId(compound.getInt("tool")), compound.getInt("damage")),
			jsonObj ->
			{
				Optional<Item> mabyItem = Registry.ITEM.getOptional(new ResourceLocation(JsonReadUtil.getString(jsonObj, "tool")));
				if (mabyItem.isPresent()) {
					return new ToolIngredient(mabyItem.get(), JsonReadUtil.getInt(jsonObj, "damage"));
				}
				return null;
			}
	));
	
	public static LazyIngredientDeserializer add(LazyIngredientDeserializer deserializer) {
		DESERIALIZATION_BY_INT_IDENTIFIER.put(deserializer.numberIdentifier, deserializer);
		DESERIALIZATION_BY_STR_IDENTIFIER.put(deserializer.stringIdentifier, deserializer);
		return deserializer;
	}
	
	public static void loadClass() {}
}
