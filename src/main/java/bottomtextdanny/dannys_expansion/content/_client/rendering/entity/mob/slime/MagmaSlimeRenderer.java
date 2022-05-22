package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.slime;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.slimes.DannySlimeModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.slimes.MagmaSlime;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MagmaSlimeRenderer extends AbstractSlimeRenderer<MagmaSlime> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/slime/magma_slime.png");

    public MagmaSlimeRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public MagmaSlimeRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DannySlimeModel<>(16, 14), 0.4F);
    }

    @Override
    protected int getBlockLightLevel(MagmaSlime entityIn, BlockPos partialTicks) {
        return 15;
    }

    public ResourceLocation getTextureLocation(MagmaSlime entity) {
        return TEXTURES;
    }


}
