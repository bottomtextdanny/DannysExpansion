package net.bottomtextdanny.de_json_generator.types;

import net.bottomtextdanny.de_json_generator.DannyGenerator;
import net.bottomtextdanny.de_json_generator.types.base.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BuiltinGenerator extends Generator<BuiltinGenerator> {
    private String itemModel = "block_item";
    private final String particle;
    private String particleID = MOD_ID;

    public BuiltinGenerator(String name) {
        super(name);
        this.particle = name;
        DannyGenerator.GUARDED_BY_PIGLINS.add(name);
        DannyGenerator.FORGE_CHESTS.add(name);
    }

    public BuiltinGenerator(String name, String particle) {
        super(name);
        this.particle = particle;
    }

    public BuiltinGenerator setItemModelTemplate(String newItemModel) {
        this.itemModel = newItemModel;
        return this;
    }

    public BuiltinGenerator itemModelTemplateNamedSame() {
        this.itemModel = this.name;
        return this;
    }

    public BuiltinGenerator particleMCID() {
        this.particleID = MC_ID;
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
        String template = getTemplate("blockstates", "s_blockstate");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));
        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File json = createBlockModel(this.name);
        String template = getTemplate("blockmodels", "invisible");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template
                .replaceAll("_particle", this.particle)
                .replaceAll("_IDparticle", this.particleID)
        );
        writer.close();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", this.itemModel);

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));
        writer.write(template.replaceAll("_name", this.name));
        writer.close();
    }
}
