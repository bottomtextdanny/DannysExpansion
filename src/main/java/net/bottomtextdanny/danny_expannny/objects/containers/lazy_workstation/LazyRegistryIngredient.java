package net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation;

import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyIngredient;
import net.bottomtextdanny.danny_expannny.objects.containers.lazy_workstation.recipe.LazyIngredientDeserializer;

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
