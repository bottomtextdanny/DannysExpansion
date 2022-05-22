package bottomtextdanny.de_json_generator.jsonBakers.itemmodel;

import com.google.gson.JsonObject;

public class FreeItemModel extends ItemModel<FreeItemModel> {
	private boolean noTexture;
	
	public FreeItemModel() {
		super("temp");
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
