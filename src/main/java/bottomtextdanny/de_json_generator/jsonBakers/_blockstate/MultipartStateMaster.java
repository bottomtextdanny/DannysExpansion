package bottomtextdanny.de_json_generator.jsonBakers._blockstate;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipartStateMaster extends JsonBaker<MultipartStateMaster> {
	public final List<BlockStateMultipart> parts = new ArrayList<>();
	
	public MultipartStateMaster(BlockStateMultipart... variants) {
		Collections.addAll(this.parts, variants);
	}
	
	public MultipartStateMaster addParts(BlockStateMultipart... parts) {
		Collections.addAll(this.parts, parts);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MultipartStateMaster bake() {
		JsonArray partArr = new JsonArray();
        this.jsonObj.add("multipart", partArr);
		
		for (BlockStateMultipart variant : this.parts) {
			partArr.add(variant.bake().decode());
		}
		
		return this;
	}
}
