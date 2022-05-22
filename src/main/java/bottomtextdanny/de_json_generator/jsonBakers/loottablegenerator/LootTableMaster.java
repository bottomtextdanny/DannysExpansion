package bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator;

import bottomtextdanny.de_json_generator.jsonBakers.JsonBaker;
import bottomtextdanny.de_json_generator.jsonBakers.JsonDecoder;
import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsFrontend;
import bottomtextdanny.de_json_generator.jsonBakers.JsonUtilsMiddleEnd;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.functions.LTFunction;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class LootTableMaster extends JsonDecoder {
	private final Set<JsonBaker> functions = new LinkedHashSet<>(0);
	private final Set<JsonBaker> pools = new LinkedHashSet<>(0);
	LootTableType type;

	public LootTableMaster(LootTableType typeEnum) {
		this.type = typeEnum;

        this.jsonObj.add("type", cString("minecraft:" + this.type.str()));
	}

	public String getType() {
		return this.type.str();
	}

	public static void main(String[] args) throws IOException {
		new LootTableMaster(LootTableType.BLOCK)
			.pools(
				new LTPool(JsonUtilsMiddleEnd.constant(1))
					.entries(
						new LTEntry()
						.itemID("minecraft:diamond")
					)
					.conditions(
						JsonUtilsFrontend.c_survives_explosion().bake()
					)
			).bake();
	}

	public LootTableMaster functions(LTFunction... aFunc) {
        this.functions.addAll(Arrays.asList(aFunc));

		return this;
	}

	public LootTableMaster pools(LTPool... aPool) {
        this.pools.addAll(Arrays.asList(aPool));

		return this;
	}

	public LootTableMaster bake() {
		if (!this.pools.isEmpty()) this.jsonObj.add("pools", cObjectCollectionBake(this.pools));
		if (!this.functions.isEmpty()) this.jsonObj.add("functions", cObjectCollection(this.functions));

		System.out.print(JsonUtilsMiddleEnd.parse(this.jsonObj));
		return this;
	}

	public String str() {
		return JsonUtilsMiddleEnd.parse(this.jsonObj);
	}

	public Set<JsonBaker> getFunctions() {
		return this.functions;
	}
}
