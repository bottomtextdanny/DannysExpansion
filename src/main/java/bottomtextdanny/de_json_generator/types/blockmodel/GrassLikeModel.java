package bottomtextdanny.de_json_generator.types.blockmodel;

import net.minecraft.resources.ResourceLocation;

public class GrassLikeModel extends TopSideDownBlockModel {

    public GrassLikeModel(String bottom) {
        super(null, null, new ResourceLocation(bottom));
        this.top = () -> new ResourceLocation(MOD_ID, this.name + "_top");
        this.side = () -> new ResourceLocation(MOD_ID, this.name + "_side");
    }
}
