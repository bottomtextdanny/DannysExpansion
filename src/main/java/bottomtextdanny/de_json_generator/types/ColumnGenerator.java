package bottomtextdanny.de_json_generator.types;

import bottomtextdanny.de_json_generator.types.base.Generator;
import bottomtextdanny.braincell.base.pair.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class ColumnGenerator extends Generator<ColumnGenerator> {
    private final String sideName;
    private final String endName;
    private Optional<Pair<String, String>> ids = Optional.of(Pair.of(MOD_ID, MOD_ID));

    public ColumnGenerator(String name, String endName, String sideName) {
        super(name);
        this.sideName = sideName;
        this.endName = endName;
    }

    public static ColumnGenerator oneFaced(String name) {
        return new ColumnGenerator(name, name, name);
    }

    public static ColumnGenerator classic(String name) {
        return new ColumnGenerator(name, name + "_end", name + "_side");
    }

    public ColumnGenerator inferIDs(String id1, String id2) {
        this.ids = Optional.of(Pair.of(id1, id2));
        return this;
    }

    public String getSideName() {
        return this.sideName;
    }

    public String getEndName() {
        return this.endName;
    }

    @Override
    public void generate() throws IOException {
        generateBlockstate();
        generateBlockModel();
        generateItemModel();
    }

    public void generateBlockstate() throws IOException {
        File json = createBlockstate(this.name);
        String template = getTemplate("blockstates", "column");

        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
                .replaceAll("_name", this.name)
        );

        writer.close();
    }

    public void generateBlockModel() throws IOException {
        File           json = createBlockModel(this.name);
        String         template = getTemplate("blockmodels", "column");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
                .replaceAll("_IDend", this.ids.get().left())
                .replaceAll("_Bend", this.endName)
                .replaceAll("_IDside", this.ids.get().right())
                .replaceAll("_Bside", this.sideName)
        );

        writer.close();
    }

    public void generateItemModel() throws IOException {
        File           json = createItemModel(this.name);
        String         template = getTemplate("itemmodels", "block_item");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
                .replaceAll("_name", this.name)
        );

        writer.close();
    }
}
