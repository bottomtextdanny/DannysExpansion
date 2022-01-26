package net.bottomtextdanny.de_json_generator.types.item;

import com.google.common.collect.Iterables;
import net.bottomtextdanny.de_json_generator.jsonBakers.itemmodel.ItemModel;
import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.IOException;

public class ItemGenerator extends Generator<ItemGenerator> {
	ItemModel model;
	
	public ItemGenerator(String name, ItemModel model) {
		super(name);
		this.model = model;
	}
	
	/**
	 * @param model ItemModel instance to be used by the created ItemGenerator.
	 *
	 * Return A new instance of ItemGenerator using the name of the first texture layer
	 * of the model in parameters.
	 *
	 * @throws IllegalArgumentException if model is null or has zero layers.
	 */
	public static ItemGenerator create(ItemModel model) {
		if (model.getLayers().size() > 0) {
			String firstLayer = Iterables.toArray(model.getLayers(), String.class)[0];
			
			return new ItemGenerator(firstLayer.substring(firstLayer.lastIndexOf('/') + 1), model);
		}
		
		throw new IllegalArgumentException("model has no texture to take the name of!");
	}
	
	@Override
	public void generate() throws IOException {
		doItemModel(this.name, this.model);
	}
}
