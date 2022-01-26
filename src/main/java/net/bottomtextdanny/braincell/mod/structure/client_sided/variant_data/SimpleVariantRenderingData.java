package net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data;

import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleVariantRenderingData<T extends LivingEntity> implements VariantRenderingData<T> {
    private final ResourceLocation texturePath;
    private final EntityModel<T> model;

    public SimpleVariantRenderingData(ResourceLocation texturePath, EntityModel<T> model) {
        super();
        this.texturePath = texturePath;
        this.model = model;
    }

    @Override
    public EntityModel<T> getModel(T entity) {
        return this.model;
    }

    @Override
    public ResourceLocation getTexture(T entity) {
        return this.texturePath;
    }
}
