package bottomtextdanny.de_json_generator.types.blockmodel;

import bottomtextdanny.de_json_generator.types.base.Generator;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

public class ColumnModel extends AbstractBlockModel {
    protected Supplier<ResourceLocation> end;
    protected Supplier<ResourceLocation> side;

    public ColumnModel(ResourceLocation end, ResourceLocation side) {
        super();
        this.end = () -> end;
        this.side = () -> side;
    }

    public ColumnModel(String end, String side) {
        super();
        this.end = () -> new ResourceLocation(end);
        this.side = () -> new ResourceLocation(side);
    }

    @Override
    public void generate() throws IOException {
        File json = Generator.createBlockModel(this.name);
        String         template = Generator.getTemplate("blockmodels", "column");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
                .replaceAll("_IDend", this.end.get().getNamespace())
                .replaceAll("_Bend", this.end.get().getPath())
                .replaceAll("_IDside", this.side.get().getNamespace())
                .replaceAll("_Bside", this.side.get().getPath())
        );

        writer.close();
    }
}
