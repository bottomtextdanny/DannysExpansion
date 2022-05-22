package bottomtextdanny.de_json_generator.types.blockmodel;

import bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleBlockModel extends AbstractBlockModel {
    private final String directory;

    public SimpleBlockModel(String directory) {
        super();
        this.directory = directory;
    }

    public SimpleBlockModel() {
        this("simple_block");
    }

    @Override
    public void generate() throws IOException {
        File json = Generator.createBlockModel(this.name);
        String         template = Generator.getTemplate("blockmodels", this.directory);
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }
}
