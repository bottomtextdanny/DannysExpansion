package net.bottomtextdanny.danny_expannny.rendering.entity.spell.bullet;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AquaticBulletEntity;
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
