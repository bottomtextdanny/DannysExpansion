package net.bottomtextdanny.de_json_generator.types;

import net.bottomtextdanny.de_json_generator.DannyGenerator;
import net.bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class FenceGenerator extends GeneratorWMother<FenceGenerator> {

    public FenceGenerator(String name, String motherName) {
        super(name, motherName);
        DannyGenerator.FENCES.add(name);
        DannyGenerator.FORGE_FENCES.add(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "fence");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name + "_side");
        String         template = getTemplate("blockmodels", "fence_side");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_post");
        template = getTemplate("blockmodels", "fence_post");
        writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_inventory");
        template = getTemplate("blockmodels", "fence_inventory");
        writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", "fence");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }
}
