package net.bottomtextdanny.de_json_generator.jsonBakers._recipehelper;

import net.bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import net.bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.conditions.LTCondition;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

public abstract class RecipeMaster<T extends RecipeMaster<T>> extends JsonBaker<T> {
	protected final LinkedHashSet<LTCondition<?>> conditions = new LinkedHashSet<>(0);
	protected SRResult result;
	protected Optional<String> group = Optional.empty();
	
	@SuppressWarnings("unchecked")
	public T group(String group) {
		this.group = Optional.of(group);
		return (T)this;
	}
	
	@SuppressWarnings("unchecked")
	public T result(SRResult result) {
		this.result = result;
		return (T)this;
	}
	
	@SuppressWarnings("unchecked")
	public T conditions(LTCondition<?>... aCon) {
        this.conditions.addAll(Arrays.asList(aCon));
		return (T)this;
	}
	
	@Override
	public T bake() {
		return (T)this;
	}
	
	public SRResult getResult() {
		return this.result;
	}
}

