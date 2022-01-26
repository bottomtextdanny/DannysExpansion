package net.bottomtextdanny.de_json_generator.types;

import net.bottomtextdanny.de_json_generator.DannyGenerator;
import net.bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class SlabGenerator extends GeneratorWMother<SlabGenerator> {

    public SlabGenerator(String name, String motherName) {
        super(name, motherName);
        DannyGenerator.SLABS.add(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "slab");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        template = P_NAME.matcher(template).replaceAll(Matcher.quoteReplacement(this.name));
        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name);
        String         template = getTemplate("blockmodels", "slab");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_top");
        template = getTemplate("blockmodels", "slab_top");
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
