package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.braincell.mod.entity.modules.variable.VariantProvider;
import net.bottomtextdanny.braincell.mod.structure.client_sided.variant_data.VariantRenderingData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public abstract class VariantRenderer<T extends Mob & VariantProvider, M extends EntityModel<T>> extends MobRenderer<T, M> {
	private final M defaultModel;

	public VariantRenderer(EntityRendererProvider.Context renderManagerIn, M entityModelIn, float shadowSizeIn) {
		super(renderManagerIn, entityModelIn, shadowSizeIn);
        this.defaultModel = entityModelIn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		if (entityIn.variableModule().isUpdated() && entityIn.variableModule().getForm() != null) {
            this.model = (M)((VariantRenderingData<T>)entityIn.variableModule().getForm().getRendering()).getModel(entityIn);
		} else {
            this.model = this.defaultModel;
		}
		
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResourceLocation getTextureLocation(T entity) {
		if (entity.variableModule().isUpdated() && entity.variableModule().getForm() != null) {
			return ((VariantRenderingData<T>)entity.variableModule().getForm().getRendering()).getTexture(entity);
		}
		return getDefaultEntityTexture(entity);
	}
	
	public abstract ResourceLocation getDefaultEntityTexture(T entity);
}
