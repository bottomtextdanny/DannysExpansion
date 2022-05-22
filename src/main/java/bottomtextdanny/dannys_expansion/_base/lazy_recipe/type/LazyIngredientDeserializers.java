package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates.ItemStackPredicate;
import bottomtextdanny.dannys_expansion._base.lazy_recipe.type.item_predicates.ItemStackPredicateType;
import bottomtextdanny.dannys_expansion._util.JsonReadUtil;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class LazyIngredientDeserializers {
	public static final Map<Integer, LazyIngredientDeserializer> DESERIALIZATION_BY_INT_IDENTIFIER = new HashMap<>();
	public static final Map<String, LazyIngredientDeserializer> DESERIALIZATION_BY_STR_IDENTIFIER = new HashMap<>();
	private static int numberIdentifier;
	
	public static final LazyIngredientDeserializer ITEM_DESERIALIZER = add(new LazyIngredientDeserializer(numberIdentifier++, "item", compound -> {
		ItemStackPredicate predicate =
				ItemStackPredicateType.getById(compound.getInt("pred"))
				.getTagDeserializer().apply(compound.getCompound("pred_e"));
		return new ItemIngredient(predicate, compound.getInt("count"));
	}, jsonObj -> {
		ItemStackPredicate predicate;
		Map.Entry<String, JsonElement> predicateElement = jsonObj.entrySet().iterator().next();
		ItemStackPredicateType<?> predType =  searchType(predicateElement.getKey());
		predicate = predType.getJsonDeserializer().apply(predicateElement.getValue());
		return new ItemIngredient(predicate, JsonReadUtil.getInt(jsonObj, "count"));
	}));

	public static final LazyIngredientDeserializer TOOL_DESERIALIZER = add(new LazyIngredientDeserializer(numberIdentifier++, "tool", compound -> {
		ItemStackPredicate predicate =
				ItemStackPredicateType.getById(compound.getInt("pred"))
						.getTagDeserializer().apply(compound.getCompound("pred_e"));
		return new ToolIngredient(predicate, compound.getInt("damage"));
	}, jsonObj -> {
		ItemStackPredicate predicate;
		Map.Entry<String, JsonElement> predicateElement = jsonObj.entrySet().iterator().next();

		ItemStackPredicateType<?> predType =  searchType(predicateElement.getKey());
		predicate = predType.getJsonDeserializer().apply(predicateElement.getValue());
		return new ItemIngredient(predicate, JsonReadUtil.getInt(jsonObj, "damage"));
	}));

	private static @NotNull ItemStackPredicateType<?> searchType(String name) {
		ItemStackPredicateType<?> type;
		if ((type = ItemStackPredicateType.getByKey(name.charAt(0))) != null) {
			return type;
		}
		return ItemStackPredicateTypes.ITEM;
	}

//	public static final LazyIngredientDeserializer TOOL_DESERIALIZER = add(new LazyIngredientDeserializer(numberIdentifier++, "tool",
//			compound -> new ToolIngredient(Item.byId(compound.getInt("tool")), compound.getInt("damage")),
//			jsonObj ->
//			{
//				Optional<Item> mabyItem = Registry.ITEM.getOptional(new ResourceLocation(JsonReadUtil.getString(jsonObj, "tool")));
//				if (mabyItem.isPresent()) {
//					return new ToolIngredient(mabyItem.get(), JsonReadUtil.getInt(jsonObj, "damage"));
//				}
//				return null;
//			}
//	));
	
	public static LazyIngredientDeserializer add(LazyIngredientDeserializer deserializer) {
		DESERIALIZATION_BY_INT_IDENTIFIER.put(deserializer.numberIdentifier, deserializer);
		DESERIALIZATION_BY_STR_IDENTIFIER.put(deserializer.stringIdentifier, deserializer);
		return deserializer;
	}
	
	public static void loadClass() {}
}
