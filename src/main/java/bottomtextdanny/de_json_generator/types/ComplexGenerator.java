package bottomtextdanny.de_json_generator.types;

import bottomtextdanny.de_json_generator.SimpleBlockstate;
import bottomtextdanny.de_json_generator.types.base.Generator;
import bottomtextdanny.de_json_generator.types.blockmodel.IGenBlockModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ComplexGenerator extends Generator<ComplexGenerator> {
    private String itemModel;
    private SimpleBlockstate customBlockstate;
    private final IGenBlockModel<?> model;

    public ComplexGenerator(String name, IGenBlockModel<?> model) {
        super(name);
        this.customBlockstate = SimpleBlockstate.NORMAL;
        this.model = model.setNameRemote(name);
        this.itemModel = "block_item";
    }

    public ComplexGenerator blockstate(SimpleBlockstate customBlockstate) {
        this.customBlockstate = customBlockstate;
        return this;
    }

    public ComplexGenerator itemModel(String itemModel) {
        this.itemModel = itemModel;
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
        this.model.generate();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", this.itemModel);
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template.replaceAll("_name", this.name));

        writer.close();
    }
}
