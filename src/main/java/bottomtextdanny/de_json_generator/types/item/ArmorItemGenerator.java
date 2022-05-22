package bottomtextdanny.de_json_generator.types.item;

import bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.IOException;

public class ArmorItemGenerator extends Generator<ArmorItemGenerator> {
	
	public ArmorItemGenerator(String name) {
		super(name);
	}
	
	@Override
	public void generate() throws IOException {
		doItemModel(this.name + "_leggings", im_simple_item(this.name + "_leggings"));
		doItemModel(this.name + "_chestplate", im_simple_item(this.name + "_chestplate"));
		doItemModel(this.name + "_leggings", im_simple_item(this.name + "_leggings"));
		doItemModel(this.name + "_boots", im_simple_item(this.name + "_boots"));
	}
}
