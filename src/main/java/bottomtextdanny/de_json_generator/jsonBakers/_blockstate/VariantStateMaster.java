package bottomtextdanny.de_json_generator.jsonBakers._blockstate;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import bottomtextdanny.de_json_generator.jsonBakers.JsonMap;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VariantStateMaster extends JsonBaker<VariantStateMaster> {
	public final List<BlockStateVariant> variants = new ArrayList<>();
	
	public VariantStateMaster(BlockStateVariant... variants) {
		Collections.addAll(this.variants, variants);
	}
	
	public static void main(String[] args) {
		VariantStateMaster master = new VariantStateMaster();
		master.addVariant(new BlockStateVariant(JsonMap.create().put("gayness", "very").put("straightness", "null")).addModels(
			new StateModel()
			.path("dannys_expansion:block/emossence")
			.yRot(90)
			,
			new StateModel()
			.path("dannys_expansion:block/plant_matter")
			.yRot(180)
			.uvLock()
			.weight(2)
			,
			new StateModel()
			.path("minecraft:block/lodestone")
			.yRot(270)
			.xRot(180)
			.weight(4)
			));
		
		System.out.println(master.bake().jsonString());
	}
	
	public VariantStateMaster addVariant(BlockStateVariant variant) {
        this.variants.add(variant);
		return this;
	}
	
	public VariantStateMaster addVariants(BlockStateVariant... variants) {
		Collections.addAll(this.variants, variants);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public VariantStateMaster bake() {
		JsonObject variantsObj = new JsonObject();
        this.jsonObj.add("variants", variantsObj);
		
		for (BlockStateVariant variant : this.variants) {
			variantsObj.add(variant.parametersAsKey, variant.bake().decode());
		}
		
		return this;
	}
}
