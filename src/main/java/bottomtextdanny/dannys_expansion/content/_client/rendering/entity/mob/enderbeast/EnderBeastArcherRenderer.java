package bottomtextdanny.dannys_expansion.content._client.rendering.entity.mob.enderbeast;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.ender_beasts.EnderBeastArcherModel;
import bottomtextdanny.dannys_expansion.content.entities.mob._pending.ender_beast.EnderBeastArcherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EnderBeastArcherRenderer extends MobRenderer<EnderBeastArcherEntity, EnderBeastArcherModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ender_beast/ender_beast_archer.png");

    public EnderBeastArcherRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public EnderBeastArcherRenderer(EntityRendererProvider.Context manager) {
        super(manager, new EnderBeastArcherModel(), 1.5F);
    }

    public ResourceLocation getTextureLocation(EnderBeastArcherEntity entity) {
        return TEXTURES;
    }
}
