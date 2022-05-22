package bottomtextdanny.de_json_generator.types.blockmodel;

import bottomtextdanny.de_json_generator.types.base.Generator;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

public class TopSideDownBlockModel extends AbstractBlockModel {
    protected Supplier<ResourceLocation> top;
    protected Supplier<ResourceLocation> side;
    protected Supplier<ResourceLocation> bottom;
    
    public TopSideDownBlockModel(ResourceLocation top, ResourceLocation side, ResourceLocation bottom) {
        super();
        this.top = () -> top;
        this.side = () -> side;
        this.bottom = () -> bottom;
    }

    @Override
    public void generate() throws IOException {
        File json = Generator.createBlockModel(this.name);
        String         template = Generator.getTemplate("blockmodels", "top_side_down");
        BufferedWriter writer = new BufferedWriter(new FileWriter(json));

        writer.write(template
                .replaceAll("_IDtop", this.top.get().getNamespace())
                .replaceAll("_Btop", this.top.get().getPath())
                .replaceAll("_IDside", this.side.get().getNamespace())
                .replaceAll("_Bside", this.side.get().getPath())
                .replaceAll("_IDbottom", this.bottom.get().getNamespace())
                .replaceAll("_Bbottom", this.bottom.get().getPath())
        );

        writer.close();
    }
}
