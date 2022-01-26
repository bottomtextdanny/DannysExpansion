package net.bottomtextdanny.de_json_generator.types.extension;

import net.bottomtextdanny.de_json_generator.GenUtils;
import net.bottomtextdanny.de_json_generator.types.ChestGenerator;
import net.bottomtextdanny.de_json_generator.types.DoorGenerator;
import net.bottomtextdanny.de_json_generator.types.SlabGenerator;
import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BlockDropGenExt implements IGenExtension<Generator> {

    @Override
    public void generate(Generator base) throws IOException {
        String content;

        if (base instanceof SlabGenerator) {
			content = GenUtils.lt_slab().bake().str();
		} else if (base instanceof DoorGenerator) {
			content = GenUtils.lt_double().bake().str();
		} else if (base instanceof ChestGenerator) {
			content = GenUtils.lt_chest().bake().str();
		} else {
			content = GenUtils.lt_classic().bake().str();
		}

        File           json = Generator.createBlockLootTable(base.getName());
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(content);

        writer.close();
    }
}
