package net.bottomtextdanny.de_json_generator.types.extension;

import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GrassLikeDropGenExt implements IGenExtension<Generator> {
    String auxiliary;
    float chance;

    public GrassLikeDropGenExt chance(float chance) {
        this.chance = chance;
        return this;
    }

    public GrassLikeDropGenExt aux(String auxiliary) {
        this.auxiliary = auxiliary;
        return this;
    }

    @Override
    public void generate(Generator base) throws IOException {
        String fileName = "grass_like";

        File json = Generator.createBlockLootTable(base.getName());
        String         template = Generator.getTemplate("loottables", fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
                .replaceAll("_Objname", base.getName())
                .replaceAll("_Varchance", String.valueOf(this.chance))
                .replaceAll("_else", this.auxiliary)
        );
        writer.close();

        this.chance = 1;
    }
}
