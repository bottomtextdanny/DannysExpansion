package bottomtextdanny.dannys_expansion.content.items;

import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISpecialModel {

    @OnlyIn(Dist.CLIENT)
    Model model();

    @OnlyIn(Dist.CLIENT)
    ResourceLocation modelTexture();
}
