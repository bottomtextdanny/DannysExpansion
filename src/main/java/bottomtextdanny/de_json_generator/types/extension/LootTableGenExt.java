package bottomtextdanny.de_json_generator.types.extension;

import bottomtextdanny.de_json_generator.GenUtils;
import bottomtextdanny.de_json_generator.jsonBakers.loottablegenerator.LootTableMaster;
import bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.IOException;

public class LootTableGenExt extends GenUtils implements IGenExtension<Generator> {
    LootTableMaster lootTable;
    String object;

    public LootTableGenExt(String object, LootTableMaster master) {
        this.lootTable = master;
        this.object = object;
    }

    @Override
    public void generate(Generator base) throws IOException {
        doLootTable(this.object, CURRENT_NAME, this.lootTable.bake());
    }
}
