package net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data;

import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface VariantRenderingData<T extends LivingEntity> {

    EntityModel<T> getModel(T entity);

    ResourceLocation getTexture(T entity);
}
