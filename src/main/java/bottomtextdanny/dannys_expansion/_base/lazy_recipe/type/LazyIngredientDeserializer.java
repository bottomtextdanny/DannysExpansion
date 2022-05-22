package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Function;

public class LazyIngredientDeserializer {
	public final Function<CompoundTag, ? extends LazyIngredient> fromNBT;
	public final Function<JsonObject, ? extends LazyIngredient> fromJSON;
	public final int numberIdentifier;
	public final String stringIdentifier;
	
	public LazyIngredientDeserializer(int numberIdentifier, String stringIdentifier, Function<CompoundTag, ? extends LazyIngredient> nbtFunc, Function<JsonObject, ? extends LazyIngredient> jsonFunc) {
		super();
		this.fromJSON = jsonFunc;
		this.fromNBT = nbtFunc;
		this.numberIdentifier = numberIdentifier;
		this.stringIdentifier = stringIdentifier;
	}
}
