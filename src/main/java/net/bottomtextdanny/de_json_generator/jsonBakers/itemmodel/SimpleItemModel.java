package net.bottomtextdanny.de_json_generator.jsonBakers.itemmodel;

import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.de_json_generator.DannyGenerator;

public class SimpleItemModel extends ItemModel<SimpleItemModel> {
	
	public SimpleItemModel() {
		super("item/generated");
	}
	
	public static SimpleItemModel deFreeDir(String... textureDirectory) {
		return new SimpleItemModel().layers(textureDirectory).bake();
	}
	
	public static SimpleItemModel deTex(String object, String... textureNames) {
		return new SimpleItemModel().layers(DEUtil.arrayPopulateArray(String.class, textureNames, e -> DannyGenerator.MOD_ID + ":" + object + "/" + e)).bake();
	}
}
