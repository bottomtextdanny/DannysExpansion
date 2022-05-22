package bottomtextdanny.de_json_generator.types;

import bottomtextdanny.de_json_generator._gen.DannyGenerator;
import bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class StairsGenerator extends GeneratorWMother<StairsGenerator> {

    public StairsGenerator(String name, String motherName) {
        super(name, motherName);
        DannyGenerator.STAIRS.add(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "stairs");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name);
        String         template = getTemplate("blockmodels", "stairs");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_inner");
        template = getTemplate("blockmodels", "stairs_inner");
        writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_outer");
        template = getTemplate("blockmodels", "stairs_outer");
        writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

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
