package net.bottomtextdanny.de_json_generator.jsonBakers.itemmodel;

import com.google.gson.JsonObject;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.de_json_generator.DannyGenerator;

public class FreeItemModel extends ItemModel<FreeItemModel> {
	private boolean noTexture;
	
	public FreeItemModel() {
		super("temp");
	}
	
	public FreeItemModel noTexture() {
        this.noTexture = true;
		return this;
	}
	
	public static FreeItemModel deFreeDir(String... textureDirectory) {
		return new FreeItemModel().layers(textureDirectory).bake();
	}
	
	public static FreeItemModel deTex(String object, String... textureNames) {
		return new FreeItemModel().layers(DEUtil.arrayPopulateArray(String.class, textureNames, e -> DannyGenerator.MOD_ID + ":" + object + "/" + e)).bake();
	}
	
	@Override
	public FreeItemModel bake() {
		JsonObject textures = new JsonObject();
		JsonObject display = new JsonObject();
		boolean[] validator = {false};

        this.jsonObj.add("parent", cString(this.parent));
		for (int i = 0; i < this.layers.size(); i++) {
			textures.add("layer"+i, cString(this.layers.get(i)));
		}
        this.matrices.forEach((s, matrixModifier) -> {
			if (matrixModifier.isModified()) {
				validator[0] = true;
				display.add(matrixModifier.getName(), matrixModifier.bake().decode());
			}
		});
		if (!isTemplate() && !this.noTexture) this.jsonObj.add("textures", textures);
		if (validator[0]) this.jsonObj.add("display", display);
		return this;
	}
}
