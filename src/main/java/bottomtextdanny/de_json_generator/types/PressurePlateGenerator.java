package bottomtextdanny.de_json_generator.types;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;
import bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PressurePlateGenerator extends GeneratorWMother<PressurePlateGenerator> {

    public PressurePlateGenerator(String name, String motherName) {
        super(name, motherName);
        DannyGenerator.PRESSURE_PLATES.add(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "pressure_plate");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name);
        String         template = getTemplate("blockmodels", "pressure_plate");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_whole", this.motherName));
        writer.close();

        json = createBlockModel(this.name + "_down");
        template = getTemplate("blockmodels", "pressure_plate_down");

        writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_whole", this.motherName));
        writer.close();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", "block_item");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }
}
