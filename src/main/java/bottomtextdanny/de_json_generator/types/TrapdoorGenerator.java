package bottomtextdanny.de_json_generator.types;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;
import bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TrapdoorGenerator extends GeneratorWMother<TrapdoorGenerator> {

    public TrapdoorGenerator(String name, String motherName) {
        super(name, motherName);
        DannyGenerator.TRAPDOORS.add(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "trapdoor");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name + "_bottom");
        String         template = getTemplate("blockmodels", "trapdoor_bottom");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));
        writer.close();

        json = createBlockModel(this.name + "_top");
        template = getTemplate("blockmodels", "trapdoor_top");
        writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));
        writer.close();

        json = createBlockModel(this.name + "_open");
        template = getTemplate("blockmodels", "trapdoor_open");
        writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));
        writer.close();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", "parent");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("\\$path", "dannys_expansion:block/" + this.name + "_bottom"));

        writer.close();
    }
}
