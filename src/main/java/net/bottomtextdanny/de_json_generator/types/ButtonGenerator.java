package net.bottomtextdanny.de_json_generator.types;

import net.bottomtextdanny.de_json_generator.DannyGenerator;
import net.bottomtextdanny.de_json_generator.types.base.GeneratorWMother;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ButtonGenerator extends GeneratorWMother<ButtonGenerator> {

    public ButtonGenerator(String name, String motherName) {
        super(name, motherName);
        DannyGenerator.BUTTONS.add(name);
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "button");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name);
        String         template = getTemplate("blockmodels", "button");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_whole", this.motherName));
        writer.close();

        json = createBlockModel(this.name + "_pressed");
        template = getTemplate("blockmodels", "button_pressed");

        writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_whole", this.motherName));
        writer.close();

        json = createBlockModel(this.name + "_inventory");
        template = getTemplate("blockmodels", "button_inventory");

        writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_whole", this.motherName));
        writer.close();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", "button");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }
}
