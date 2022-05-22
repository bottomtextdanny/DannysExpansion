package bottomtextdanny.de_json_generator.types;

import bottomtextdanny.de_json_generator.SimpleBlockstate;
import bottomtextdanny.de_json_generator.types.base.Generator;
import bottomtextdanny.de_json_generator.types.blockmodel.SimpleBlockModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FullblockGenerator extends Generator<FullblockGenerator> {
    private SimpleBlockstate customBlockstate;

    public FullblockGenerator(String name) {
        super(name);
        this.customBlockstate = SimpleBlockstate.NORMAL;
    }

    public FullblockGenerator custom(SimpleBlockstate customBlockstate) {
        this.customBlockstate = customBlockstate;
        return this;
    }


    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", this.customBlockstate.getDir());

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));
        writer.close();
    }

    public void generateBlockModel() throws IOException {
        new SimpleBlockModel().setNameRemote(this.name).generate();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", "block_item");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }
}
