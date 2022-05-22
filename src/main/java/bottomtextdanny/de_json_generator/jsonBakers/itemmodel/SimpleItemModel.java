package bottomtextdanny.de_json_generator.jsonBakers.itemmodel;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;

import java.util.Arrays;

public class SimpleItemModel extends ItemModel<SimpleItemModel> {
	
	public SimpleItemModel() {
		super("item/generated");
	}
	
	public static SimpleItemModel deFreeDir(String... textureDirectory) {
		return new SimpleItemModel().layers(textureDirectory).bake();
	}
	
	public static SimpleItemModel deTex(String object, String... textureNames) {
		return new SimpleItemModel().layers(Arrays.stream(textureNames)
				.map(e -> DannyGenerator.MOD_ID + ":" + object + "/" + e).toArray(String[]::new)).bake();
	}
}
