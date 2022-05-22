package bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile.bullet;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.entities.projectile.bullet.AquaticBulletEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AquaticBulletRenderer extends BulletRenderer<AquaticBulletEntity> {

    public AquaticBulletRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public AquaticBulletRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(AquaticBulletEntity entity) {
        return new ResourceLocation(DannysExpansion.ID, "textures/entity/bullet/aquatic_bullet.png");
    }
}
