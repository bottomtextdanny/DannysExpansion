package net.bottomtextdanny.de_json_generator.types;

import net.bottomtextdanny.de_json_generator.DannyGenerator;
import net.bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class FenceGateGenerator extends GeneratorWMother<FenceGateGenerator> {

    public FenceGateGenerator(String name, String motherName) {
        super(name, motherName);
        DannyGenerator.FENCE_GATES.add(name);
        DannyGenerator.FORGE_FENCE_GATES.add(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "fence_gate");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name);
        String         template = getTemplate("blockmodels", "fence_gate");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_open");
        template = getTemplate("blockmodels", "fence_gate_open");
        writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_wall");
        template = getTemplate("blockmodels", "fence_gate_wall");
        writer = new BufferedWriter(new FileWriter(json));

        writer.write(P_WHOLE.matcher(template).replaceAll(Matcher.quoteReplacement(this.motherName)));

        writer.close();

        json = createBlockModel(this.name + "_wall_open");
        template = getTemplate("blockmodels", "fence_gate_wall_open");
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
