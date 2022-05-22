package bottomtextdanny.dannys_expansion._base.lazy_recipe.type;

public abstract class LazyRegistryIngredient implements LazyIngredient {
	public final LazyIngredientDeserializer deserializationIdentifier;
	
	public LazyRegistryIngredient(LazyIngredientDeserializer identifier) {
		super();
        this.deserializationIdentifier = identifier;
	}
	
	public LazyIngredientDeserializer getDeserializationIdentifier() {
		return this.deserializationIdentifier;
	}
}
