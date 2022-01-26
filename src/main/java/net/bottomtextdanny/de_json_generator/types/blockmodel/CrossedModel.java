package net.bottomtextdanny.de_json_generator.types.blockmodel;

import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CrossedModel extends AbstractBlockModel {

    @Override
    public void generate() throws IOException {
        File json = Generator.createBlockModel(this.name);
        String         template = Generator.getTemplate("blockmodels", "simple_block");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }
}
