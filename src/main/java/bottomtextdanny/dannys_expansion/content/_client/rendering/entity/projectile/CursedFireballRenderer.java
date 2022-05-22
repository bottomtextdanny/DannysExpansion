package bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles.CursedFireballModel;
import bottomtextdanny.dannys_expansion.content.entities.projectile.CursedFireball;
import com.mojang.blaze3d.vertex.PoseStack;
import bottomtextdanny.braincell.mod.rendering.BCRenderTypes;
import bottomtextdanny.braincell.mod.rendering.SpellRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CursedFireballRenderer extends SpellRenderer<CursedFireball, CursedFireballModel> {
    public ResourceLocation TEXTURE_PATH = new ResourceLocation(DannysExpansion.ID, "textures/entity/magic/cursed_fireball.png");

    public CursedFireballRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public CursedFireballRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CursedFireballModel());
    }

    @Override
    public void render(CursedFireball entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, 15728880);
    }

    @Override
    public RenderType getRenderType(CursedFireball entityIn) {
        return BCRenderTypes.getFlatShading(getTextureLocation(entityIn));
    }

    @Override
    public ResourceLocation getTextureLocation(CursedFireball entity) {
        return this.TEXTURE_PATH;
    }
}
